package com.example.pintuan.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.deepoove.poi.XWPFTemplate;
import com.example.pintuan.VO.ActivityExportVO;
import com.example.pintuan.VO.ActivityFeeExportVO;
import com.example.pintuan.VO.FeeVO;
import com.example.pintuan.entity.Activity;
import com.example.pintuan.entity.Fee;
import com.example.pintuan.service.ActivityService;
import com.example.pintuan.service.FeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/fee")
@Api(tags = "缴纳团费")
public class FeeController {
    @Resource
    private FeeService activityFeeService;
    @Resource
    private ActivityService activityService;

    @PostMapping("/payment")
    @ApiOperation(value = "缴纳费用")
    public R aaPayment(@RequestBody FeeVO feeParam){
        Fee fee = new Fee();
        BeanUtils.copyProperties(feeParam,fee);
        fee=feeParam.getIsFee()==1?fee.setAaFee(feeParam.getFee()):fee.setActivityFee(feeParam.getFee());
        //查询是否之前在这个活动上缴纳过
        Fee one = activityFeeService.getOne(new QueryWrapper<Fee>().eq("member_id", feeParam.getMemberId())
                .eq("activity_id", feeParam.getActivityId()));
        if (Objects.nonNull(one)){
            //追加即可
            if (Objects.nonNull(fee.getAaFee())) {
                if (Objects.isNull(one.getAaFee())) {
                    one.setAaFee(fee.getAaFee());
                } else {
                    one.setAaFee(one.getAaFee().add(fee.getAaFee()));
                }
            }
            if (Objects.nonNull(fee.getActivityFee())) {
                if (Objects.isNull(one.getActivityFee())) {
                    one.setActivityFee(fee.getActivityFee());
                } else {
                    one.setActivityFee(one.getActivityFee().add(fee.getActivityFee()));
                }
            }
            activityFeeService.updateById(one);
        }else {
            activityFeeService.save(fee);
        }
        return R.ok("缴纳成功");
    }

    @PostMapping("/paymentPage")
    @ApiOperation(value = "根据不同角色分页查询活动费用")
    public R activityPage(@RequestBody FeeVO feeParam){
        feeParam.setPage((feeParam.getPage()-1)*feeParam.getPage());
        List<Fee> activityFees = activityFeeService.selectListPage(feeParam);
        for (Fee activityFee : activityFees) {
            Activity byId = activityService.getById(activityFee.getActivityId());
            activityFee.setActivityName(byId.getActivityName());
            int id = activityService.count(new QueryWrapper<Activity>().eq("id", activityFee.getActivityId()));

            if (Objects.nonNull(activityFee.getAaFee())){
                activityFee.setRealTimeAaFee(activityFee.getAaFee().divide(BigDecimal.valueOf(id), 2, RoundingMode.HALF_UP));
            }else {
                activityFee.setRealTimeAaFee(new BigDecimal(BigInteger.ZERO));
            }
            if (Objects.nonNull(activityFee.getActivityFee())){
                activityFee.setRealTimeCostFee(activityFee.getActivityFee().add(activityFee.getRealTimeAaFee()));
            }else {
                activityFee.setRealTimeCostFee(activityFee.getRealTimeAaFee());
            }
        }
        long count=activityFeeService.selectListCount(feeParam);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data",activityFees);
        map.put("total",count);
        return R.ok(map);
    }

    @PostMapping("")
    @ApiOperation(value = "团费导出")
    public void getExportExcel(HttpServletResponse response) throws IOException {
        List<Fee> list = activityFeeService.list(null);
        ArrayList<ActivityExportVO> activityVOS = new ArrayList<>();
        for (Fee activityFee : list) {
            Activity byId = activityService.getById(activityFee.getActivityId());
            activityFee.setActivityName(byId.getActivityName());
            int id = activityService.count(new QueryWrapper<Activity>().eq("id", activityFee.getActivityId()));
            if (Objects.nonNull(activityFee.getAaFee())){
                activityFee.setRealTimeAaFee(activityFee.getAaFee().divide(BigDecimal.valueOf(id), 2, RoundingMode.HALF_UP));
            }else {
                activityFee.setRealTimeAaFee(new BigDecimal(BigInteger.ZERO));
            }
            if (Objects.nonNull(activityFee.getActivityFee())){
                activityFee.setRealTimeCostFee(activityFee.getActivityFee().add(activityFee.getRealTimeAaFee()));
            }else {
                activityFee.setRealTimeCostFee(activityFee.getRealTimeAaFee());
            }
            ActivityExportVO activityVO = new ActivityExportVO();
            BeanUtils.copyProperties(activityFee, activityVO);
            activityVOS.add(activityVO);
        }

        // 创建Excel文件
        String fileName = "团费.xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(activityVOS, writeSheet);
        excelWriter.finish();

        // 下载Excel文件
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        try (OutputStream outputStream = response.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        }
    }
    @PostMapping("/wordExport/{id}")
    @ApiOperation(value = "word导出")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getWordExport(HttpServletResponse response, @PathVariable(name = "id") Integer id) throws Exception {
        Fee fee = activityFeeService.getById(id);
        Activity activity = activityService.getById(fee.getActivityId());
        fee.setActivityName(activity.getActivityName());
        if (Objects.nonNull(fee.getAaFee())) {
            fee.setRealTimeAaFee(fee.getAaFee().divide(BigDecimal.valueOf(id), 2, RoundingMode.HALF_UP));
        } else {
            fee.setRealTimeAaFee(BigDecimal.ZERO);
        }
        if (Objects.nonNull(fee.getActivityFee())) {
            fee.setRealTimeCostFee(fee.getActivityFee().add(fee.getRealTimeAaFee()));
        } else {
            fee.setRealTimeCostFee(fee.getRealTimeAaFee());
        }
      ActivityFeeExportVO   activityFeeVO = new ActivityFeeExportVO();
        BeanUtils.copyProperties(fee, activityFeeVO);
        activityFeeVO.setMainContent(activity.getMainContent());
        // 导出word并指定word导出模板
        HashMap<String, Object> map = new HashMap<>();
        map.put("activityName", activityFeeVO.getActivityName());
        map.put("mainContent", activityFeeVO.getMainContent());
        map.put("realTimeAaFee", activityFeeVO.getRealTimeAaFee());
        map.put("activityFee", activityFeeVO.getActivityFee());
        map.put("realTimeCostFee", activityFeeVO.getRealTimeCostFee());

        XWPFTemplate template = XWPFTemplate.compile("/temp/moban.docx");
        template.render(map);
        FileOutputStream out = new FileOutputStream("fee.docx");
        template.write(out);
        // 创建输入流资源对象
        FileInputStream fileInputStream = new FileInputStream(new File("fee.docx"));
        InputStreamResource resource = new InputStreamResource(fileInputStream);

        // 设置响应头信息
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.docx");
        System.out.println("运行了");
        // 返回带有流的响应实体
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}

