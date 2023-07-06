<template>
    <div class="data">
        <n-data-table :columns="columns" :data="list" />
    </div>
    <div class="foot">
        <n-pagination :item-count="total" :page-size="limit" :on-update:page="updateData" />
    </div>
</template>

<script setup lang="ts">
import { h, onMounted, ref } from 'vue';
import { NButton } from 'naive-ui';
import request from "@/api/api";
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
        title: "团队名称",
        width: 100,
        align: 'center',
        key: "teamName",
        render: (_: any, index: number) => {
            return index + 1
        }
    },
    {
        title: "创建时间",
        width: 100,
        align: 'center',
        key: "creationTime",
        render: (_: any, index: number) => {
            return index + 1
        }
    },
    {
        title: "操作",
        align: 'center',
        render: (row: { teamId: any; }) => h(NButton, {
            type: 'primary',
            onClick: async () => {
                await request({
                    url: "/api/team-member/join",
                    method: "post",
                    data: {
                        teamId: row.teamId,
                        userId: userId
                    }
                }).then((res: { code: number; msg: any; }) => {
                    // @ts-ignores
                    if (res.code === 0) {
                        getData();
                        // @ts-ignore
                        ElMessage.success(res.msg);
                    } else {
                        // @ts-ignore
                        ElMessage.error(res.msg);
                    }
                })
            }
        }, "确认加入")
    }
]
const list = ref([])
const page = ref(1)
const limit = ref(10)
const total = ref(0)
const userId = localStorage.getItem('id') ? Number(localStorage.getItem('id')) : 0;
const tableData = ref<any[]>([]);
const getData = async () => {
    let reqData: any = {
        page: page.value,
        limit: limit.value,
        userId: userId
    }
    await request({
        url: '/api/team-member/getListOfNoJoin',
        method: 'post',
        data: reqData
    }).then((res: { data: { data: any[]; total: number; }; }) => {
        tableData.value = res.data.data;
        total.value = res.data.total;
    })
}
const updateData = (page: number) => {
    page = page
    getData()
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