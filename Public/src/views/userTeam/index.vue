<template>
    <div class="data">
        <n-data-table :columns="columns" :data="list" />
    </div>
    <div class="foot">
        <n-pagination :item-count="total" :page-size="limit" :on-update:page="updateData" />
    </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import request from "@/api/api";

const list = ref([])
const total = ref(0)
const limit = ref(10)
const tableData = ref<any[]>([]);
const page = ref(1)
const userId = localStorage.getItem('id')
const columns = [
    {
        title: "序号",
        width: 100,
        align: 'center',
        key: 'index',
        render: (_: any, index: number) => {
            return index + 1
        }
    },
    {
        title: '团队名称',
        key: 'teamName',
        width: 320,
        align: 'center'
    },
    {
        title: '创建日期',
        render: (row: any) => {
            return new Date(row.creationTime).toLocaleDateString()
        }
    },
]
const updateData = (page: number) => {
    page = page
    getData()
}
const getData = async () => {
    await request({
        url: '/api/team-member/getListOfJoin',
        method: 'post',
        data: {
            userId: Number(userId),
            page: page.value,
            limit: limit.value
        }
    }).then(res => {
        console.log(res)
        tableData.value = res.data.data
    }).catch(err => {
        console.log(err)
    })
}
onMounted(() => {
    getData()
})
</script>

<style scoped>
.foot {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 2rem;
}
</style>