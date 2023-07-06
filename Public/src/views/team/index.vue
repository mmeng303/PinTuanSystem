<template>
    <div class="seach">
        <n-input placeholder="请输入用户名" class="inpput" v-model:value="searchValue" />
        <n-button type="primary" @click="getData">搜索</n-button>
        <!-- 新增 -->
        <n-button type="primary" class="add" @click="addUser">新增</n-button>
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
import { NButton, NInput, NDataTable, NPagination, NForm, NFormItem, NSelect } from 'naive-ui'
import { AddTeamApi, QueryTeamListApi, ReleaseTeamApi, QueryUserApi, InviteMemberApi } from "./api"
import { useDialog, useMessage } from 'naive-ui'
import { TableColumn } from 'naive-ui/es/data-table/src/interface';
const dialog = useDialog()
const message = useMessage()
const searchValue = ref('')
const selectTeamId = ref<number | undefined>(undefined)
const activityInfo = ref<any>({})
const selectUser = ref<any>({})
const columns: TableColumn[] = [
    {
        title: "序号",
        width: 100,
        align: 'center',
        render: (_: any, index: number) => {
            return index + 1
        },
        key: 'index'
    },
    {
        title: '团队名称',
        key: 'teamName',
        width: 320,
        align: 'center'
    },
    {
        title: '创建日期',
        width: 320,
        key: 'creationTime',
        render: (row: any) => {
            return new Date(row.creationTime).toLocaleDateString()
        }
    },
    {
        title: '操作',
        key: 'action',
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
                            selectTeamId.value = row.id
                            dialog.create({
                                title: '发布团队',
                                content: () => h(NForm, {
                                    'label-width': '120px',
                                }, [
                                    h(NFormItem, {
                                        label: '活动名称',
                                        class: 'form-item',
                                    }, {
                                        default: () => h(NInput, {
                                            placeholder: '请输入活动名称',
                                            class: 'input',
                                            onChange: (value: string) => {
                                                activityInfo.value.activityName = value
                                            }
                                        })
                                    }),
                                    h(NFormItem, {
                                        label: '活动起点',
                                        class: 'form-item',
                                    }, {
                                        default: () => h(NInput, {
                                            placeholder: '起点',
                                            class: 'input',
                                            onChange: (value: string) => {
                                                activityInfo.value.startingPoint = value
                                            }
                                        })
                                    }),
                                    h(NFormItem, {
                                        label: '主要内容',
                                        class: 'form-item',
                                    }, {
                                        default: () => h(NInput, {
                                            placeholder: '请输入主要内容',
                                            class: 'input',
                                            onChange: (value: string) => {
                                                activityInfo.value.mainContent = value
                                            }
                                        })
                                    }),
                                ]),
                                positiveText: '确定',
                                negativeText: '取消',
                                onPositiveClick: async () => {
                                    await ReleaseTeamApi({
                                        id: undefined,
                                        teamId: selectTeamId.value as number,
                                        activityName: activityInfo.value.activityName,
                                        startPoint: activityInfo.value.startingPoint,
                                        mainContent: activityInfo.value.mainContent,
                                        isCompleted: 0,
                                        startTime: '',
                                    }).then(res => {
                                        if (res.code === 0) {
                                            message.success('发布成功')
                                            getData()
                                        } else {
                                            message.error('发布失败')
                                        }
                                    })
                                },
                            })
                        }
                    },
                    {
                        default: () => '发布'
                    }
                ),
                h(
                    NButton,
                    {
                        type: 'primary',
                        size: 'small',
                        class: 'btn',
                        onClick: () => {
                            // @ts-ignore
                            selectTeamId.value = row.id
                            dialog.create({
                                title: '修改团队名称',
                                content: () => h(NInput, {
                                    placeholder: '请输入团队名称',
                                    class: 'input',
                                    onChange: (value: string) => {
                                        addTeamName.value = value
                                    }
                                }),
                                positiveText: '确定',
                                negativeText: '取消',
                                onPositiveClick: async () => {
                                    await AddTeamApi(addTeamName.value, selectTeamId.value).then(res => {
                                        if (res.code === 0) {
                                            message.success('修改成功')
                                            getData()
                                        } else {
                                            message.error('修改失败')
                                        }
                                    })
                                },
                            })
                        }
                    },
                    {
                        default: () => '修改'
                    }
                ),
                h(
                    NButton,
                    {
                        type: 'primary',
                        size: 'small',
                        class: 'btn',
                        onClick: () => {
                            selectTeamId.value = row.id
                            dialog.create({
                                title: "邀请",
                                content: () => h(NSelect, {
                                    placeholder: '请选择',
                                    class: 'input',
                                    options: userList.value,
                                    onUpdateValue: (value: any) => {
                                        selectUser.value = value;
                                    }
                                },),
                                positiveText: '确定',
                                onPositiveClick: async () => {
                                    await InviteMemberApi(selectTeamId.value as number, selectUser.value).then(res => {
                                        if (res.code === 0) {
                                            message.success('邀请成功')
                                            getData()
                                        } else {
                                            message.error('邀请失败')
                                        }
                                    })
                                },
                            })
                        }
                    },
                    {
                        default: () => '邀请'
                    }
                ),
            ])
        }
    }
]
const list = ref([])
const total = ref(0)
const limit = ref(10)
const page = ref(1)
const userList = ref([])
const updateData = (page: number) => {
    console.log(page)
}
onMounted(async () => {
    await getData()
    await getUserData()
})
const getUserData = async () => {
    await QueryUserApi().then(res => {
        userList.value = res.data.map((v: { username: any; id: any; }) => {
            return {
                label: v.username,
                value: v.id
            }
        })
    })
}
const addTeamName = ref<string>('')
const getData = async () => {
    await QueryTeamListApi({
        page: page.value,
        limit: limit.value,
        teamName: searchValue.value
    }).then(res => {
        list.value = res.data.data
        total.value = res.data.total
    })
}
const addUser = () => {
    dialog.create({
        title: '新增团队',
        content: () => h('div', {
            style: {
                width: '400px'
            }
        }, [
            h(NInput, {
                placeholder: '请输入团队名称',
                onChange: (value: string) => {
                    addTeamName.value = value
                }
            })
        ]),
        positiveText: '确定',
        negativeText: '取消',
        onPositiveClick: async () => {
            await AddTeamApi(addTeamName.value).then(res => {
                if (res.data.code === 0) {
                    message.success('新增成功')
                    getData()
                } else {
                    message.error('新增失败')
                }
            })
        },
        onNegativeClick: () => {
            console.log('点击了取消')
        }
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

.add {
    margin-left: 1rem;
}
</style>

<style>
.action {
    display: flex;
    align-items: center;
}

.action button {
    margin-right: 0.5rem;
}
</style>