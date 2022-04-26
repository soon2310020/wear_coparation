<template>
  <div>
    <div id="page-wrapper">
      <div>
        <b-form class="row">
          <div class="row">
            <div class="col-lg-12">
              <h1 class="page-header">Quản lý sản phẩm</h1>
            </div>
            <!-- /.col-lg-12 -->
          </div>
          <b-form-group class="col-lg-3 col-md-12">
            <el-input
              placeholder="Nhập tên sản phẩm.."
              v-model="formSearch.name"
            ></el-input>
          </b-form-group>
          <b-form-group class="col-lg-3 col-md-12">
            <el-select
              v-model="formSearch.categoryIds"
              multiple
              placeholder="Nhập loại sản phẩm"
            >
              <el-option
                v-for="item in listCategory"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </b-form-group>

          <b-form-group class="col-lg-3 col-md-12">
            <el-input
            @input="NumberToString1(formSearch.fromPrice,'fromPrice')"
              placeholder="Số tiền từ"
              v-model="formSearch.fromPrice"
            ></el-input>
          </b-form-group>

          <b-form-group class="col-lg-3 col-md-12">
            <el-input
            @input="NumberToString1(formSearch.toPrice,'toPrice')"
              placeholder="Đến số tiền"
              v-model="formSearch.toPrice"
            ></el-input>
          </b-form-group>
          <div class="col-lg-12 col-md-12" style="text-align: center">
            <b-button type="button" variant="primary" @click="search"
              >Tìm kiếm</b-button
            >
            <el-button type="button" @click="test()">Thêm mới</el-button>
          </div>
        </b-form>
      </div>
      <div>
        <el-table :data="items" style="width: 100%">
          <el-table-column prop="id" label="Mã sản phẩm" width="150">
          </el-table-column>
          <el-table-column prop="title" label="Tên sản phẩm" width="150">
          </el-table-column>
          <el-table-column prop="" label="Ảnh">
            <template slot-scope="scope">
              <img
                class="custum-image-table"
                v-if="scope.row.file != null"
                :src="
                  'data:' +
                  scope.row.contentType +
                  ';base64,' +
                  scope.row.fileBase64
                "
              />
            </template>
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
      :title="active == 'create' ? 'Thêm mới sản phẩm' : 'Chi tiết sản phẩm'"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="productCreate">
        <el-form-item label="Tên sản phẩm:" :label-width="formLabelWidth">
          <el-input v-model="productCreate.title" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="Kích cỡ:" :label-width="formLabelWidth">
          <el-select
            v-model="productCreate.size"
            multiple
            filterable
            allow-create
            default-first-option
            placeholder="Chọn hoặc nhập size"
          >
            <el-option
              v-for="(item, index) in optionsSize"
              :key="index"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Giá:" :label-width="formLabelWidth">
          <el-input
            @keypress.native="formatInput($event)"
            @input="NumberToString(productCreate.price)"
            v-model="productCreate.price"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item label="Mô tả:" :label-width="formLabelWidth">
          <ckeditor
            v-model="productCreate.content"
            :config="editorConfig"
          ></ckeditor>
        </el-form-item>

        <el-form-item label="File :" :label-width="formLabelWidth">
          <el-upload
            class="upload-demo"
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            :on-change="handleAddFile"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
          >
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{ file }">
              <!-- {{file}} -->

              <img
                class="el-upload-list__item-thumbnail"
                :src="'data:' + file.contentType + ';base64,' + file.content"
                alt=""
              />
              <span class="el-upload-list__item-actions">
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleRemove(file)"
                >
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="" />
          </el-dialog>
        </el-form-item>

        <el-form-item label="Loại sản phẩm:" :label-width="formLabelWidth">
          <el-select
            v-model="productCreate.categoryId"
            placeholder="Nhập loại sản phẩm"
          >
            <el-option
              v-for="item in listCategory"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Giảm giá:" :label-width="formLabelWidth">
          <el-input
            v-model="productCreate.discount"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">Hủy</el-button>
        <el-button type="primary" @click="createProduct">Lưu</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
// import moment from 'moment'
import { format, fromUnixTime } from 'date-fns'
import _ from 'lodash'
export default {
  created () {
    this.getInitForm()
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
      this.productCreate.id = row.id
      this.productCreate.title = row.title
      this.productCreate.price = this.NumberToString(row.price)
      this.productCreate.content = row.content
      this.productCreate.discount = row.discount
      this.productCreate.size = row.size.trim().split(' ')
      this.productCreate.categoryId = row.category.id
      if (row.file != null) {
        let array = row.file.trim().split(' ')
        console.log(array)
        array.forEach((element) => {
          axios.get(this.baseURL + `/file/${element}`).then(
            (res) => {
              this.fileList.push(res.data)
              this.fileIdList.push(res.data.id)
            },
            (error) => {
              console.log(error)
            }
          )
        })
      }
    },
    handleDelete (id) {
      axios.delete(this.baseURL + '/product/' + id).then(
        (res) => {
          this.$toast.open('Xóa sản phẩm ' + id + ' thành công !')
          this.search()
        },
        (error) => {
          console.log(error)
          this.$toast.error(
            'Tồn tại sản phẩm có loại sản phẩm trên không thể xóa !'
          )
        }
      )
    },
    handleAddFile (file, fileList) {
      if (
        file.name.includes('jpg') ||
        file.name.includes('jpeg') ||
        file.name.includes('png')
      ) {
        // this.fileList = []
        const fileReader = new FileReader()
        fileReader.readAsDataURL(file.raw)
        fileReader.onload = (e) => {
          const dataRaw = e.target.result
          const base64Data = dataRaw.substr(
            dataRaw.indexOf('base64,') + 'base64,'.length
          )
          const fileUpload = {
            content: base64Data,
            contentType: file.raw.type,
            name: file.name,
            url: '',
            extendsion: 'Create',
            id: null
          }
          console.log(fileUpload)
          axios.post(this.baseURL + '/file/upload', fileUpload).then(
            (res) => {
              this.fileList.push(res.data)
              this.fileIdList.push(res.data.id)
            },
            (error) => {
              console.log(error)
              this.$toast.error('File quá nặng vui lòng chọn dưới 5 MB !')
              fileList.splice(fileList.length - 1, 1)
            }
          )
        }
      } else {
        this.$toast.error('Vui lòng chọn định dạng ảnh!')
        fileList.splice(fileList.length - 1, 1)
      }
    },
    handleRemove (file) {
      let index = this.fileList.findIndex((x) => x.id === file.id)
      console.log(index)
      this.fileList.splice(index, 1)
      this.fileIdList.splice(index, 1)
      // console.log(file, fileList);
    },
    handlePreview (file, fileList) {
      console.log(file, fileList)
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
      this.productCreate = {}
      this.fileList = []
    },
    getInitForm () {
      axios
        .get(this.baseURL + `/categories/category?name=&page=0&size=1000`)
        .then((response) => {
          this.listCategory = response.data.content
        })
        .catch((e) => {
          // this.errors.push(e);
        })
    },

    search () {
      this.items = []
      let query = Object.assign({}, this.formSearch)
      query.fromPrice =
        this.formSearch.fromPrice == null
          ? null
          : this.stringToNumber(this.formSearch.fromPrice)
      query.toPrice =
        this.formSearch.toPrice == null
          ? null
          : this.stringToNumber(this.formSearch.toPrice)
      axios
        .post(
          this.baseURL +
            `/product/search-product?page=${this.page}&size=${this.size}`,
          query
        )
        .then((response) => {
          const awaitFun = (content) => {
            const promises = content.map(async (e, index) => {
              if (e.file != null) {
                let array = e.file.trim().split(' ')
                console.log(array)
                await axios.get(this.baseURL + `/file/${array[0]}`).then(
                  (res) => {
                    e.fileBase64 = res.data.content
                    e.contentType = res.data.contentType
                  },
                  (error) => {
                    console.log(error)
                  }
                )
              }
            })
            return Promise.all(promises)
          }
          // this.items = ;
          let newObj = _.cloneDeep(response.data.content)
          awaitFun(newObj).then(() => {
            this.items = newObj
          })

          // response.data.content.forEach((e, index) => {
          //   let array = e.file.trim().split(" ");
          //   axios.get(this.baseURL + `/${array[0]}`).then(
          //     (res) => {
          //       this.items[index].fileBase64 = res.data.content;
          //       this.items[index].contentType = res.data.contentType;
          //     },
          //     (error) => {
          //       console.log(error);
          //     }
          //   );
          // });
          this.totalElements = response.data.totalElements
        })
        .catch((e) => {
          // this.errors.push(e);
        })
    },

    createProduct () {
      if (this.productCreate.title === '' || this.productCreate.title == null) {
        this.$toast.error('Không được để trống tên sản phẩm !')
        return
      }
      this.productCreate.price = this.stringToNumber(this.productCreate.price)
      this.productCreate.file = this.fileIdList
      axios
        .post(this.baseURL + `/product/product`, this.productCreate)
        .then((response) => {
          if (this.active === 'create') {
            this.$toast.open('Thêm mới thành công !')
          } else {
            this.$toast.open('Sửa thành công !')
          }
          // this.$toastr.zs('SUCCESS MESSAGE', 'Thêm mới thành công !')
          this.dialogFormVisible = false
          this.productCreate = {}
          this.active = 'create'
          this.search()
          // this.dialogFormVisible = !this.dialogFormVisibles
        })
        .catch((e) => {
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
      fileIdList: [],
      listCategory: [],
      formSearch: {
        name: null,
        categoryIds: [],
        fromPrice: null,
        toPrice: null
      },
      productCreate: {
        id: null,
        title: null,
        price: null,
        content: null,
        discount: null,
        size: null,
        categoryId: null
      },
      formLabelWidth: '120px',
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
