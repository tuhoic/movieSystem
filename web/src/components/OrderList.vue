<template>
  <div class="order-list">
    <h2>订单列表</h2>
    <table>
      <thead>
      <tr>
        <th>订单号</th>
        <th>电影名称</th>
        <th>电影票数</th>
        <th>总金额</th>
        <th>订单状态</th>
        <th>下单时间</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="order in orders" :key="order.orderId">
        <td>{{ order.orderId }}</td>
        <td>{{ order.movieName }}</td>
        <td>{{ order.ticketNumber }}</td>
        <td>{{ order.totalPrice }}</td>
        <td>{{ order.status }}</td>
        <td>{{ formatDate(order.createTime) }}</td>
        <td>
          <button v-if="order.status === '待支付'" @click="abolishOrder(order)">取消订单</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { getOrderList, cancelOrder } from '@/api/order'

export default {
  name: 'OrderList',
  setup() {
    const orders = ref([])

    const formatDate = (dateStr) => {
      const date = new Date(dateStr)
      return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`
    }

    const fetchOrders = async () => {
      orders.value = await getOrderList()
    }

    const abolishOrder = async (order) => {
      if (confirm('确认取消订单吗？')) {
        await cancelOrder(order.orderId)
        await fetchOrders()
      }
    }

    onMounted(fetchOrders)

    return {
      orders,
      formatDate,
      abolishOrder,
    }
  },
}
</script>

<style scoped>
.order-list {
  margin-top: 40px;
}

table {
  width: 100%;
  border-collapse: collapse;
  text-align: center;
}

th {
  font-weight: bold;
  background-color: #eee;
  padding: 8px;
}

td {
  padding: 8px;
  border: 1px solid #ddd;
}

button {
  background-color: #f44336;
  color: #fff;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #d32f2f;
}
</style>
