<template>
    <div class="data">
        <div class="view">
            <n-card title="已完结活动个数" size="huge" class="card">
                <span>
                    {{ count1 }}
                </span>
            </n-card>
            <n-card title="用户个数" size="huge" class="card">
                <span>
                    {{ count2 }}
                </span>
            </n-card>
        </div>
        <div class="charts">
            <div id="myEcharts" :style="{ width: '50%', height: '300px' }"></div>
            <div id="myEcharts2" :style="{ width: '50%', height: '300px' }"></div>
        </div>
    </div>
</template>

<script setup lang="ts">
import * as echarts from "echarts";
import { onMounted, ref } from 'vue';
import { GetPieData, GetColumnData, GetActivityCountApi, GetUserCount } from "./api";

const count1 = ref(0);
const count2 = ref(0);
onMounted(async () => {
    let data = await getPie();
    let data2 = await getColums();
    let chartDom = document.getElementById('myEcharts');
    let chartDom2 = document.getElementById('myEcharts2');
    let myChart = echarts.init(chartDom as HTMLDivElement);
    let myChart2 = echarts.init(chartDom2 as HTMLDivElement);
    let option = {
        title: {
            text: 'Referer of a Website',
            subtext: 'Fake Data',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
                name: 'Access From',
                type: 'pie',
                radius: '50%',
                data: [
                    ...data.data
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    }
    myChart.setOption(option);
    let option2 = {
        xAxis: {
            type: 'category',
            data: ['第一季度', '第二季度', '第三季度', '第四季度']
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: [0, 0, 0, 0],
                type: 'bar',
                showBackground: true,
                backgroundStyle: {
                    color: 'rgba(180, 180, 180, 0.2)'
                }
            }
        ]
    }
    myChart2.setOption(option2);
    await getView()
})
const getPie = async () => {
    let data = await GetPieData()
    return data
}
const getColums = async () => {
    let data = await GetColumnData()
    return data
}
const getView = async () => {
    let c1 = await GetActivityCountApi()
    count1.value = c1.data
    let c2 = await GetUserCount()
    count2.value = c2.data
}
</script>

<style scoped>
.data {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.charts {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    margin-top: 2rem;
}

.view {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-around;
}

.card {
    width: 40%;
}

.card span {
    font-weight: bold;
    font-size: 30px;
}
</style>