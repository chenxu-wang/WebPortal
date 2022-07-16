import axios from 'axios'

// config
const axiosOption = {
    baseURL: 'http://localhost:8081/Backend',
    timeout: 5000,
}

// create an instance
const instance = axios.create(axiosOption);

// add interceptor
instance.interceptors.request.use(function (config) {
    let token = localStorage.getItem('token')
    if(token){
        config.headers = {
            'token': token,
            'Content-type': 'application/json; charset=utf-8'
        }
    }
    return config;
}, function (error) {
    // do sth
    return Promise.reject(error);
});

// add response interceptors
instance.interceptors.response.use(function (response) {
    // do sth
    return response.data;
}, function (error) {
    // do sth with rejection
    return Promise.reject(error);
});

export default instance;