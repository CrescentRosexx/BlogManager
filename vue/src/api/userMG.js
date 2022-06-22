import axios from 'axios';
import { loginreq, req } from './axiosFun';

// 登录接口
export const login = (params) => { return loginreq("post", "/api/login", params) };
// 获取用户菜单
export const menu = (params) => { return axios.get("/api/menu?&token=" + localStorage.getItem('logintoken')).then(res => res.data) };
// 退出接口
export const loginout = () => { return axios.delete("/api/login?&token=" + localStorage.getItem('logintoken')).then(res => res.data) };

/**
 * 用户管理
 **/
// 用户管理-获取用户列表
export const userList = (params) => { return req("post", "/api/User/list", params) };
// 用户管理-保存（添加）
export const userAdd = (params) => { return req("post", "/api/User/add", params) };
// 用户管理-保存（修改）
export const userEdit = (params) => { return req("post", "/api/User/edit", params) };
// 用户管理-删除用户
export const userDelete = (params) => { return axios.delete("/api/User/delete?userid=" + params + "&token=" + localStorage.getItem('logintoken')).then(res => res.data) };
//用户管理-批量删除
export const userBatchDelete = (params) => { return axios.delete("/api/User/batchDelete?ids=" + params + "&token=" + localStorage.getItem('logintoken')).then(res => res.data) };
