<template>
    <div class="seach">
        <n-input placeholder="请输入活动名称" class="inpput" v-model:value="searchValue" />
        <n-button type="primary" @click="getData">搜索</n-button>
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
import { NButton, NInput, NDataTable, NPagination, NTag } from 'naive-ui'
import { QueryActivityList, PaymentApi, SettlementApi } from "./api"
import { Payment } from './util';
import { useDialog, useMessage } from 'naive-ui'
import { TableColumn } from 'naive-ui/es/data-table/src/interface';
const dialog = useDialog()
const searchValue = ref('')
const total = ref(0)
const limit = ref(10)
const page = ref(1)
const userId = localStorage.getItem('id')
const checkedValue = ref('1')
const money = ref(0)
const message = useMessage()
const columns: TableColumn[] = [
    {
        title: "序号",
        render: (_row: any, index: number) => {
            return index + 1
        },
        key: 'index',
    },
    {
        title: "活动名称",
        key: 'activityName',
        width: 280
    },
    {
        title: "开始时间",
        width: 280,
        render: (row: any) => {
            return new Date(row.startTime).toLocaleString()
        },
        key: 'startTime'
    },
    {
        title: '操作',
        key: 'action',
        width: 200,
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
                        onClick: () => {
                            dialog.success({
                                title: "详情",
                                content: () => h('div', {
                                    class: 'detail'
                                }, [
                                    h('div', {
                                        class: 'detail-item'
                                    }, [
                                        h('span', {
                                            class: 'detail-title'
                                        }, '活动名称：'),
                                        h('span', {
                                            class: 'detail-content'
                                        }, row.activityName || "暂无名称")
                                    ]),
                                    h('div', {
                                        class: 'detail-item'
                                    }, [
                                        h('span', {
                                            class: 'detail-title'
                                        }, '活动地点：'),
                                        h('span', {
                                            class: 'detail-content'
                                        }, row.startPoint)
                                    ]),
                                    h('div', {
                                        class: 'detail-item'
                                    }, [
                                        h('span', {
                                            class: 'detail-title'
                                        }, '活动时间：'),
                                        h('span', {
                                            class: 'c'
                                        }, new Date(row.startTime).toLocaleString())
                                    ]),
                                    h('div', {
                                        class: 'detail-item'
                                    }, [
                                        h('span', {
                                            class: 'detail-title'
                                        }, '活动内容：'),
                                        h('span', {
                                            class: 'detail-content'
                                        }, row.mainContent || "暂无内容")
                                    ]),
                                    h('div', {
                                        class: 'detail-item'
                                    }, [
                                        h('span', {
                                            class: 'detail-title'
                                        }, '是否结算:'),
                                        h('span', {
                                            class: 'detail-content'
                                        }, h(NTag, {
                                            type: row.isSettle ? 'success' : 'error'
                                        }, {
                                            default: () => row.isCompleted == 1 ? '已结算' : '未结算'
                                        }))
                                    ]),
                                ]),
                            })
                        }
                    },
                    {
                        default: () => '详情'
                    }
                ),
                h(
                    NButton,
                    {
                        type: 'primary',
                        size: 'small',
                        class: 'btn',
                        onClick: () => {
                            dialog.create({
                                title: "缴纳团费",
                                content: () => h('div',
                                    {
                                        class: 'pay'
                                    },
                                    [
                                        h('div', {
                                            class: 'pay-item'
                                        }, [
                                            h('label', {
                                                class: 'pay-label',
                                                for: 'pay-type'
                                            }, [
                                                h('input', {
                                                    type: 'radio',
                                                    name: 'pay-type',
                                                    value: '1',
                                                    checked: checkedValue.value == '1',
                                                    onChange: (e: any) => {
                                                        checkedValue.value = e.target.value
                                                    }
                                                }),
                                                h('span', 'AA缴纳团费')
                                            ]),
                                            h('label', {
                                                class: 'pay-label',
                                                for: 'pay-type'
                                            }, [
                                                h('input', {
                                                    type: 'radio',
                                                    name: 'pay-type',
                                                    value: '2',
                                                    checked: checkedValue.value == '2',
                                                    onChange: (e: any) => {
                                                        checkedValue.value = e.target.value
                                                    }
                                                }),
                                                h('span', '个人缴纳团费')
                                            ]),
                                        ]),
                                        h('div', {
                                            class: 'pay-item'
                                        }, [
                                            h('span', '缴纳金额：'),
                                            h(NInput, {
                                                placeholder: '请输入缴纳金额',
                                                class: 'pay-input',
                                                onChange: (e: any) => {
                                                    money.value = e
                                                }
                                            })
                                        ]),
                                    ]
                                ),
                                positiveText: '确定',
                                onPositiveClick: () => {
                                    let reqData: Payment = {
                                        activityId: row.id,
                                        memberId: Number(userId),
                                        isFee: Number(checkedValue.value),
                                        fee: money.value
                                    }
                                    PaymentApi(reqData).then(res => {
                                        if (res.code == 0) {
                                            message.success('缴纳成功')
                                        } else {
                                            message.error('缴纳失败')
                                        }
                                    })
                                }
                            })
                        }
                    },
                    {
                        default: () => '缴纳团费'
                    }
                ),
                h(
                    NButton,
                    {
                        type: 'primary',
                        size: 'small',
                        class: 'btn',
                        onClick: () => {

                        }
                    },
                    {
                        default: () => '修改'
                    }
                ),
                h(
                    NButton,
                    {
                        type: 'info',
                        size: 'small',
                        class: 'btn',
                        onClick: async () => {
                            dialog.create({
                                title: "确定要结算活动吗？",
                                positiveText: "确定",
                                onPositiveClick: async () => {
                                    let res = await SettlementApi(row.id)
                                    if (res.code == 0) {
                                        message.success('结算成功')
                                        await getData()
                                    } else {
                                        message.error('结算失败')
                                    }
                                },
                                negativeText: "取消"
                            })
                        }
                    },
                    {
                        default: () => '结算'
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
    let data = await QueryActivityList({
        page: page.value,
        limit: limit.value,
        activityName: searchValue.value == "" ? undefined : searchValue.value,
        userId: Number(userId)
    })
    list.value = data.data.data
    total.value = data.data.total
}

onMounted(async () => {
    await getData()
})

</script>

<style >
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

.action {
    display: flex;
    align-items: center;
}

.action button {
    margin-right: 0.5rem;
}

.detail-title {
    margin-right: 0.5rem;
}

.pay {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.pay-item {
    display: flex;
    align-items: center;
    margin-top: 1rem;
}

.pay-item span {
    white-space: nowrap;
}

.pay-label {
    margin-right: 2rem;
    display: flex;
    align-items: center;
}

.pay-label span {
    margin-left: 0.5rem;
}

.pay-btn {
    margin-top: 1rem;
}
</style>