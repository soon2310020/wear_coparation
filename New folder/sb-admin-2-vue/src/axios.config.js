import axios from 'axios'

let user = window.sessionStorage.getItem('user')
axios.defaults.headers = {
  'Access-Control-Allow-Origin': '*',
  'Content-type': 'application/json',
  'Authorization': user
}
const onRequestSuccess = (config) => {
  let user = window.sessionStorage.getItem('user')
  axios.defaults.headers = {
    'Access-Control-Allow-Origin': '*',
    'Content-type': 'application/json',
    'Authorization': user
  }
  return config
}
const setupAxiosInterceptors = (onUnauthenticated) => {
  const onResponseError = (err) => {
    const status = err.status || err.response.status
    if (status === 401) {
      console.log('status 401')
      let loc = window.location
      const port = loc.port ? ':' + loc.port : ''
      loc.href = `//${loc.hostname}${port}/#/login`
      sessionStorage.clear()
      // history.pushState({}, null, '/Login');
      // location.reload();
      // onUnauthenticated();
    }
    if (status === 403) {
      console.log('status 403')
      this.$router.push('/dashborad')
      // history.pushState({}, null, '/Login');
      // location.reload();
      // onUnauthenticated();
    }
    return Promise.reject(err)
  }
  if (axios.interceptors) {
    axios.interceptors.request.use(onRequestSuccess)
    axios.interceptors.response.use((res) => res, onResponseError)
  }
}
export function initVueApp (vue) {
  setupAxiosInterceptors(() => console.log('Unauthorized!'))
}

// export { onRequestSuccess, setupAxiosInterceptors };
