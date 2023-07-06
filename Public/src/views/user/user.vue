<template>
    <div class="seach">
        <n-input clearable placeholder="请输入用户名" class="inpput" v-model:value="searchValue" />
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
import { onMounted } from 'vue';
import { ref, h } from 'vue';
import { AddUserApi, QueryUserListApi } from './api';
import { NForm, NFormItem, NInput, NTag } from 'naive-ui'
import { useDialog,useMessage } from 'naive-ui'
const message = useMessage()
const dialog = useDialog()
const list = ref([])
const columns = ref([
    {
        title: "用户ID",
        key: "id"
    },
    {
        title: '姓名',
        key: 'name'
    },
    {
        title: "用户名",
        key: "username"
    },
    {
        title: "身份",
        // 根据authority字段判断身份
        render: (row: any) => {
            // 使用tag包裹，根据不同的身份显示不同的颜色
            return row.authority == 2 ? h(NTag, { type: 'success' }, { default: () => '普通用户' }) : h(NTag, { type: 'warning' }, { default: () => '管理员' })

        }
    },
    {
        title: "密码",
        render: (row: any) => {
            // 默认显示*****,点击显示密码
            return h('div', {
                onClick: () => {
                    row.showPassword = !row.showPassword
                }
            }, {
                default: () => row.showPassword ? row.password : '******'
            })
        }
    }
])
const limit = ref(10)
const page = ref(1)
const total = ref(0)
const searchValue = ref('')
const getData = async () => {
    let data = await QueryUserListApi({
        limit: limit.value,
        page: page.value,
        name: searchValue.value == '' ? undefined : searchValue.value
    })
    data.data.data.forEach((item: any) => {
        item.showPassword = false
    })
    list.value = data.data.data;
    total.value = data.data.total;
}
onMounted(async () => {
    await getData()
})

const updateData = async (p: number) => {
    page.value = p
    await getData()
}
const addUserInfo = ref({
    name: '',
    username: '',
    password: '',
    authority: 2
})
const addUser = () => {
    // 跳转到新增页面
    dialog.create({
        title: '新增用户',
        content: () => h(NForm, {
            labelWidth: 100,
            labelPlacement: 'left',
            model: addUserInfo.value
        }, [
            h(NFormItem, { label: '姓名', path: addUserInfo.value.name }, {
                default: () => h(NInput, {
                    placeholder: "输入姓名", onChange: (e) => {
                        addUserInfo.value.name = e
                    }
                })
            }),
            h(NFormItem, { label: '用户名', path: addUserInfo.value.username }, {
                default: () => h(NInput, {
                    placeholder: "输入用户名", onChange: (e) => {
                        addUserInfo.value.username = e
                    }
                })
            }),
            h(NFormItem, { label: '密码', path: addUserInfo.value.password }, {
                default: () => h(NInput, {
                    placeholder: "输入密码", onChange: (e) => {
                        addUserInfo.value.password = e
                    }
                })
            }),
            h(NFormItem, { label: '身份' }, { default: () => h(NTag, "团员") }),
        ]),
        onPositiveClick: async () => {
            // 点击确定按钮，调用新增接口
            await AddUserApi({
                name: addUserInfo.value.name,
                username: addUserInfo.value.username,
                password: addUserInfo.value.password,
                authority: 2,
                id: undefined
            }).then((res) => {
                if (res.data.code == 0) {
                    getData();
                    message.success('新增成功')
                }
            })
        },
        positiveText: '确定',
        negativeText: '取消',
        onNegativeClick: () => {
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
    margin-left: 12px;
}
</style>