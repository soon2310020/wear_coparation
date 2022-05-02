<template>
  <div>
    <div id="page-wrapper">
      <div>
        <b-form class="row">
          <div class="row">
            <div class="col-lg-12">
              <h1 class="page-header">Quản lý đơn hàng</h1>
            </div>
            <!-- /.col-lg-12 -->
          </div>
          <b-form-group class="col-lg-6 col-md-12">
            <el-input
              placeholder="Nhập tên sản phẩm.."
              v-model="formSearch.productName"
            ></el-input>
          </b-form-group>
          <b-form-group class="col-lg-6 col-md-12">
            <el-select
              v-model="formSearch.status"
              multiple
              placeholder="Trạng thái"
            >
              <el-option
                v-for="item in statusOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </b-form-group>

          <div class="col-lg-12 col-md-12" style="text-align: center">
            <b-button type="button" variant="primary" @click="search"
              >Tìm kiếm</b-button
            >
          </div>
        </b-form>
      </div>
      <div>
        <el-table :data="items" style="width: 100%">
          <el-table-column prop="id" label="Mã đặt hàng" width="150">
          </el-table-column>
            <el-table-column prop="receivedName" label="Nguời nhận hàng" width="150">
          </el-table-column>
          <el-table-column prop="address" label="Địa chỉ" width="150">
          </el-table-column>
          <el-table-column prop="mobile" label="Số điện thoại" width="150">
          </el-table-column>
          <el-table-column prop="createAt" label="Ngày tạo" width="140">
            <template slot-scope="scope">
              <span>
                {{
                  scope.row.createdAt
                    ? formatDates(`${scope.row.createdAt}`)
                    : ""
                }}</span
              >
            </template>
          </el-table-column>
          <el-table-column prop="updateAt" label="Ngày cập nhật">
            <template slot-scope="scope">
              <span>
                {{
                  scope.row.updatedAt
                    ? formatDates(`${scope.row.updatedAt}`)
                    : ""
                }}</span
              >
            </template>
          </el-table-column>
          <el-table-column label="Thao tác">
            <template slot-scope="scope">
              <el-button type="primary" @click="handleDetail(scope.row)"
                >Chi tiết</el-button
              >
              <el-button type="danger" @click="handleDelete(scope.row.id)"
                >Xóa</el-button
              >
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          current-page.sync="5"
          :page-size="size"
          layout="total, prev, pager, next"
          :total="totalElements"
        >
        </el-pagination>
      </div>
    </div>
    <el-dialog :title="'Chi tiết đơn hàng'" :visible.sync="dialogFormVisible">
      <el-form :model="orderDetails">
        <el-form-item label="Địa chỉ nhận hàng:" :label-width="formLabelWidth">
          <el-input
            v-model="orderDetails.address"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
         <el-form-item label="Tên Người nhận:" :label-width="formLabelWidth">
          <el-input
            v-model="orderDetails.receivedName"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
         <el-form-item label="Số điện thoại:" :label-width="formLabelWidth">
          <el-input
            v-model="orderDetails.address"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>

        <el-form-item label="Tổng thanh toán:" :label-width="formLabelWidth">
          <el-input
            @keypress.native="formatInput($event)"
            @input="NumberToString(orderDetails.priceTotal)"
            v-model="orderDetails.priceTotal"
            autocomplete="off"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="Trạng thái:" :label-width="formLabelWidth">
          <el-select v-model="orderDetails.status" placeholder="Select">
            <el-option
              v-for="item in statusOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
        <el-table :data="orderDetails.orderDetails" style="width: 100%">
          <el-table-column prop="product.title" label="Tên sản phẩm" width="150">
          </el-table-column>
          <el-table-column prop="product.price" label="Giá sản phẩm" width="150">
            <template slot-scope="scope">
              <span>
                {{
                  scope.row.product
                    ? converToMoney(`${scope.row.product.price}`)
                    : ""
                }}</span
              >
            </template>
          </el-table-column>
          <el-table-column prop="size" label="Size" width="150">
          </el-table-column>
          <el-table-column prop="quatity" label="Số lượng" width="140">

          </el-table-column>
        </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">Hủy</el-button>
        <el-button type="primary" @click="save">Lưu</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
// import moment from 'moment'
import { format, fromUnixTime } from 'date-fns'
export default {
  created () {
    this.search()
  },
  methods: {
    save () {
      axios
        .put(
          this.baseURL +
            `/order/status?id=${this.orderDetails.id}&status=${this.orderDetails.status}`
        )
        .then((response) => {
          this.$toast.open('Cập nhật thành công!')
          this.dialogFormVisible = false
          this.search()
          this.resertFrom()
        })
        .catch((e) => {
          // this.errors.push(e);
        })
    },
    resertFrom () {
      this.orderDetails = {
        id: null,
        address: null,
        priceTotal: null,
        username: null,
        status: null
      }
    },
    handleSizeChange (val) {
      this.size = val
      // this.search()
    },
    handleCurrentChange (val) {
      this.page = val - 1
      // this.search()
    },
    formatDates (cellValue) {
      // let a = new Date(cellValue);
      const dataFormat = format(fromUnixTime(cellValue), 'dd/MM/yyyy')
      return dataFormat
    },
    handleDetail (row) {
      this.dialogFormVisible = true
      setTimeout(() => {
        this.orderDetails.id = row.id
        this.orderDetails.address = row.address
        this.orderDetails.priceTotal = this.NumberToString(row.priceTotal)
        this.orderDetails.receivedName = row.receivedName ? row.receivedName : ''
        this.orderDetails.status = row.status
        this.orderDetails.orderDetails = row.orderDetails
        // this.orderDetails.size = row.size.trim().split(' ')
        // this.orderDetails.categoryId = row.category.id
      }, '100')
    },
    test () {
      this.dialogFormVisible = !this.dialogFormVisible
    },
    cancel () {
      this.dialogFormVisible = !this.dialogFormVisible
      this.productCreate = {}
      this.fileList = []
    },

    search () {
      axios
        .post(
          this.baseURL + `/order/search?page=${this.page}&size=${this.size}`,
          this.formSearch
        )
        .then((response) => {
          this.items = response.data.content
        })
        .catch((e) => {
          // this.errors.push(e);
        })
    },
    converToMoney (value) {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
    },
    formatInput (evt) {
      evt = evt || window.event
      var charCode = evt.which ? evt.which : evt.keyCode
      if (
        (charCode > 31 && (charCode < 48 || charCode > 57)) ||
        charCode === 32
      ) {
        evt.preventDefault()
      } else {
        return true
      }
    },

    NumberToString1 (value, key) {
      if (value == null || value === '') {
      } else {
        value = this.stringToNumber(value)
        if (!isNaN(value)) {
          let val = (value / 1).toFixed().replace('.', ',')
          let string = val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
          this.formSearch[key] = string
          return string
        } else {
        }
      }
    },

    NumberToString (value, k) {
      if (value == null || value === '') {
      } else {
        value = this.stringToNumber(value)
        if (!isNaN(value)) {
          let val = (value / 1).toFixed().replace('.', ',')
          let string = val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
          this.orderDetails.price = string
          return string
        } else {
        }
      }
    },
    stringToNumber (string) {
      if (string == null || string === '') {
        return null
      } else {
        if (typeof string === 'number') {
          return string
        }
        return parseInt(string.replace(/[.,\/#!$%\^&\*;:{}=\_`~()]/g, ''))
      }
    }
  },
  name: 'Forms',
  data () {
    return {
      formSearch: {
        productName: '',
        status: []
      },
      orderDetails: {
        id: null,
        address: null,
        priceTotal: null,
        username: null,
        status: null,
        user: null,
        orderDetails: []
      },
      fileList: [],
      active: 'create',
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      // baseURL: 'http://192.168.1.208:9999/wear_shop/api',
      baseURL: 'http://localhost:9999/wear_shop/api',
      dialogFormVisible: false,
      items: [],
      optionsSize: ['M', 'L', 'XL'],
      statusOptions: [
        { id: 1, name: 'Đặt hàng' },
        { id: 2, name: 'Đang vận chuyển' },
        { id: 3, name: 'Đã nhận hàng' },
        { id: 4, name: 'Đã hủy' }
      ],
      formLabelWidth: '150px',
      editorConfig: {},
      totalElements: 0,
      page: 0,

      size: 10
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style >
.upload-demo .el-upload__input {
  display: none !important;
}
.custum-image-table {
  width: 200px;
}
</style>
