<template>
  <div>
    <div id="page-wrapper">
      <div>
        <b-form class="row">
          <div class="row">
            <div class="col-lg-12">
              <h1 class="page-header">Quản lý tài khoản</h1>
            </div>
            <!-- /.col-lg-12 -->
          </div>
          <b-form-group class="col-lg-6 col-md-12">
            <el-input
              placeholder="Nhập username."
              v-model="formSearch.name"
            ></el-input>
          </b-form-group>

          <div class="col-lg-6 col-md-12" style="text-align: center">
            <b-button type="button" variant="primary" @click="search"
              >Tìm kiếm</b-button
            >
            <el-button type="button" @click="test()">Thêm mới</el-button>
          </div>
        </b-form>
      </div>
      <div>
        <el-table :data="items" style="width: 100%">
          <el-table-column prop="id" label="Mã tài khoản" width="150">
          </el-table-column>
          <el-table-column prop="username" label="Tên tài khoản" width="150">
          </el-table-column>
          <el-table-column prop="createAt" label="Ngày tạo" width="140">
            <template slot-scope="scope">
              <span>
                {{
                  scope.row.createAt ? formatDates(`${scope.row.createAt}`) : ""
                }}</span
              >
            </template>
          </el-table-column>
          <el-table-column prop="updateAt" label="Ngày cập nhật">
            <template slot-scope="scope">
              <span>
                {{
                  scope.row.createAt ? formatDates(`${scope.row.createAt}`) : ""
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
    <el-dialog
      :title="active == 'create' ? 'Thêm mới tài khoản' : 'Chi tiết tài khoản'"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="userCreate">
        <el-form-item label="username:" :label-width="formLabelWidth">
          <el-input v-model="userCreate.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Tên :" :label-width="formLabelWidth">
          <el-input v-model="userCreate.firstName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Họ :" :label-width="formLabelWidth">
          <el-input v-model="userCreate.lastName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Vai trò :" :label-width="formLabelWidth">
        <el-select v-model="userCreate.role" placeholder="Select">
        <el-option
         v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value">
         </el-option>
         </el-select>
        </el-form-item>
          <el-form-item label="Password :" prop="pass" :label-width="formLabelWidth">
          <el-input type="password" v-model="userCreate.password" autocomplete="off" ></el-input>
        </el-form-item>
          <el-form-item label="Nhập lại Password :" prop="pass" :label-width="formLabelWidth">
          <el-input type="password" v-model="userCreate.repassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
            prop="email"
            label="Email" :label-width="formLabelWidth"
             :rules="[
      { type: 'email', message: 'Hãy nhập email của bạn', trigger: ['blur', 'change'] }]">
    <el-input v-model="userCreate.email"></el-input>
  </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">Hủy</el-button>
        <el-button type="primary" @click="createUser">Lưu</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
// import moment from 'moment'
import { format, fromUnixTime } from 'date-fns'
import { ro } from 'date-fns/locale'
export default {
  created () {
    console.log('a')
    this.search()
  },
  methods: {
    formatDates (cellValue) {
      // let a = new Date(cellValue);
      const dataFormat = format(fromUnixTime(cellValue), 'dd/MM/yyyy')
      return dataFormat
    },
    handleDetail (row) {
      this.active = 'edit'
      this.fileList = []
      this.fileIdList = []
      this.dialogFormVisible = true
      // this.productCreate = Object.assign({}, row);
      this.userCreate.id = row.id
      this.userCreate.username = row.username
      this.userCreate.password = ''
      this.userCreate.repassword = ''
      this.userCreate.firstName = row.firstName
      this.userCreate.lastName = row.lastName
      this.userCreate.role = row.role
      this.userCreate.email = row.email
      console.log(this.userCreate)
    },
    handleDelete (id) {
      axios.delete(this.baseURL + '/user/' + id).then(
        (res) => {
          this.$toast.open('Xóa tài khoản ' + id + ' thành công !')
          this.search()
        },
        (error) => {
          console.log(error)
          this.$toast.error(
            'Tồn tại đơn đặt hàng của tài khoản trên không thể xóa !'
          )
        }
      )
    },

    handleSizeChange (val) {
      this.size = val
      this.search()
    },
    handleCurrentChange (val) {
      this.page = val - 1
      this.search()
    },
    test () {
      this.dialogFormVisible = !this.dialogFormVisible
    },
    cancel () {
      this.dialogFormVisible = !this.dialogFormVisible
      this.userCreate = {}
      this.fileList = []
    },

    search () {
      this.items = []

      this.fileList = []
      axios
        .get(
          this.baseURL +
            `/user/search?page=${this.page}&size=${this.size}&name=${this.formSearch.name}`
        )
        .then((response) => {
          this.items = response.data.content
          this.totalElements = response.data.totalElements
        })
        .catch((e) => {
          // this.errors.push(e);
        })
    },

    createUser () {
      const usernameRegex = new RegExp('^[a-z0-9A-Z]{1,}$')
      if (this.userCreate.username === '' || this.userCreate.username == null) {
        this.$toast.error('username không được để trống!')
        return
      }
      if (!usernameRegex.test(this.userCreate.username)) {
        this.$toast.error('username Không được chứa ký tự đặc biệt!')
        return
      }
      if (this.userCreate.role === '' || this.userCreate.role == null) {
        this.$toast.error('Vai trò không được để trống!')
        return
      }

      if (this.userCreate.password === '' || this.userCreate.password == null) {
        this.$toast.error('password không được để trống!')
        return
      }
      if (this.userCreate.password !== this.userCreate.repassword) {
        this.$toast.error('password nhập lại không giống password!')
        return
      }

      axios
        .post(this.baseURL + `/user/register`, this.userCreate)
        .then((response) => {
          if (this.active === 'create') {
            this.$toast.open('Thêm mới tài khoản thành công !')
          } else {
            this.$toast.open('Sửa thành công !')
          }
          this.dialogFormVisible = false
          this.userCreate = {}
          this.active = 'create'
          this.search()
          // this.dialogFormVisible = !this.dialogFormVisibles
        })
        .catch((e) => {
          this.$toast.error('username đã tồn tại vui lòng chọn username khác!')
          // this.errors.push(e);
        })
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
    NumberToString (value, k) {
      if (value == null || value === '') {

      } else {
        value = this.stringToNumber(value)
        if (!isNaN(value)) {
          let val = (value / 1).toFixed().replace('.', ',')
          let string = val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
          this.productCreate.price = string
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
      options: [{
        value: 'ROLE_ADMIN',
        label: 'Admin'
      }, {
        value: 'ROLE_USER',
        label: 'User'
      }],
      fileList: [],
      active: 'create',
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      // baseURL: 'http://192.168.1.208:9999/wear_shop/api',
      baseURL: 'http://localhost:9999/wear_shop/api',
      dialogFormVisible: false,
      items: [],
      formSearch: {
        name: ''

      },
      userCreate: {
        username: null,
        password: null,
        repassword: null,
        firstName: null,
        lastName: null,
        email: null,
        role: null
      },
      formLabelWidth: '150px',
      editorConfig: {
        // The configuration of the editor.
      },
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
