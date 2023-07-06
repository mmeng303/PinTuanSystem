<template>
    <div class="seach">
        <n-button type="primary" @click="exportWord">导出</n-button>
    </div>
    <div class="data">
        <n-data-table :columns="columns" :data="list" />
    </div>
    <div class="foot">
        <n-pagination :item-count="total" :page-size="limit" :on-update:page="updateData" />
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, h } from 'vue'
import { NButton, NDataTable, NPagination } from 'naive-ui'
import { ReportTourFareApi, exportTourFareList, getTourFareList } from "./api"
import { TableColumn } from 'naive-ui/es/data-table/src/interface';
const total = ref(0)
const limit = ref(10)
const page = ref(1)
const userId = localStorage.getItem('id')
const columns: TableColumn[] = [
    {
        title: "序号",
        width: 100,
        render: (_row: any, index: number) => {
            return index + 1
        },
        align: 'center',
        key: 'index'
    },
    {
        title: "活动名称",
        key: 'activityName',
        width: 200,
        align: 'center'
    },
    {
        title: "活动AA费用",
        width: 160,
        key: "aaFee"
    },
    {
        title: "个人费用",
        key: "activityFee",
        width: 160
    },
    {
        title: "总计费用",
        key: "realTimeCostFee",
        width: 160
    },
    {
        title: '操作',
        key: 'action',
        align: 'center',
        render: (row: any) => {
            return h('div', {
                class: 'action'
            }, [
                h(
                    NButton,
                    {
                        type: 'primary',
                        size: 'small',
                        class: 'btn',
                        onClick: async () => {
                            await ReportTourFareApi(row.id).then((res: BlobPart) => {
                                const blob = new Blob([res], { type: 'application/msword' })
                                const downloadElement = document.createElement('a')
                                const href = window.URL.createObjectURL(blob) // 创建下载的链接
                                downloadElement.href = href
                                downloadElement.download = `${row.activityName}报告.docx` // 下载后文件名
                                document.body.appendChild(downloadElement)
                                downloadElement.click() // 点击下载
                                document.body.removeChild(downloadElement) // 下载完成移除元素
                                window.URL.revokeObjectURL(href) // 释放掉blob对象
                            })
                        }
                    },
                    {
                        default: () => '生成报告'
                    }
                ),
            ])
        }
    }
]
const list = ref([])
const updateData = (page: number) => {
    console.log(page)
}
const getData = async () => {
    let data = await getTourFareList({
        page: page.value,
        limit: limit.value,
        memberId: Number(userId),
    })
    list.value = data.data.data
    total.value = data.data.total
}

onMounted(async () => {
    await getData()
})
const exportWord = async () => {
    await exportTourFareList().then(res => {
        console.log(res);
        const blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' })
        const downloadElement = document.createElement('a')
        const href = window.URL.createObjectURL(blob) // 创建下载的链接
        downloadElement.href = href
        downloadElement.download = `团费.xlsx` // 下载后文件名
        document.body.appendChild(downloadElement)
        downloadElement.click() // 点击下载
        document.body.removeChild(downloadElement) // 下载完成移除元素
        window.URL.revokeObjectURL(href) // 释放掉blob对象
    })
}
</script>

<style scoped>
.foot {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 2rem;
}

.seach {
    display: flex;
    align-items: center;
    margin-bottom: 2rem;
}

.inpput {
    width: 320px;
    margin-right: 1rem;
}
</style>
