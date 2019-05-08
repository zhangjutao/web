import axios from 'axios'

const Axios = axios.create();

// export const login = (val) => {
//     //return Axios.get(baseURLStr + '/query/versionBaseinfo'+"?version="+val
//     //).then(res => res.data)
// }

export const getWebkitJson = (model,path) => {
  return Axios({
    url: 'https://echarts.baidu.com/data/asset/data/webkit-dep.json',
    method: 'get'
  }).then(res => res.data)
}