import request from './request'
//login
export const LoginApi = (params)=> request.post('/login',params)
//links list
export const queryLinkApi = (params)=> request.post('/weblinks/queryByCat',params)
//delete link
export const deleteLinkApi = (params)=> request.post('/weblinks/delete',params)
//update link
export const updateLinkApi = (params)=> request.post('/weblinks/update',params)
//update link
export const createLinkApi = (params)=> request.post('/weblinks/create',params)
