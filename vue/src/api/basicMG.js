import axios from 'axios';
import {loginreq, req} from './axiosFun';

/**
 * 博客管理
 **/
// 博客管理-获取博客列表
export const articleList = (params) => {
  return req("post", "/api/Article/list", params)
};
// 博客管理-获取某文章的标签列表
export const labelList = (params) => {
  return axios.post("/api/Article/labels?articleid=" + params).then(res => res.data)
};
// 博客管理-博客对应的标签修改
export const updateLabel = (params) => {
  return req("post", "/api/Article/labelUpdate", params)
};
// 博客管理-审核状态修改
export const updateAudit = (articleid, audit) => {
  return axios.post("/api/Article/audit?articleid=" + articleid + "&audit=" + audit).then(res => res.data)
};
//博客管理-删除博客文章
export const articleDetele = (articleid) => {
  return axios.delete("/api/Article/delete?articleid=" + articleid).then(res => res.data)
};
//博客管理-批量删除博客文章
export const articleBatchDetele = (ids) => {
  return axios.delete("/api/Article/batchDelete?ids=" + ids).then(res => res.data)
};
//博客管理-写博客
export const addArticle = (params) => {
  return req("post", "/api/Article/add", params)
};

/**
 * 点赞管理
 **/
// 点赞管理-获取点赞列表
export const likeList = (params) => {
  return req("post", "/api/Like/list", params)
};
//点赞管理-删除点赞记录
export const likeDetele = (likeid) => {
  return axios.delete("/api/Like/delete?likeid=" + likeid).then(res => res.data)
};
//点赞管理-批量删除点赞记录
export const likeBatchDetele = (ids) => {
  return axios.delete("/api/Like/batchDelete?ids=" + ids).then(res => res.data)
};
/**
 * 标签管理
 **/
// 标签管理-获取标签列表
export const allLabel = (params) => {
  return req("post", "/api/Label/list", params)
};
//标签管理-添加标签
export const addLabel = (labelname) => {
  return axios.post("/api/Label/add?labelname=" + labelname).then(res => res.data)
};
//标签管理-更新标签
export const updateLabelname = (params) => {
  return req("post", "/api/Label/update", params)
};
//标签管理-删除标签
export const labelDetele = (labelid) => {
  return axios.delete("/api/Label/delete?labelid=" + labelid).then(res => res.data)
};
//标签管理-批量删除标签
export const labelBatchDetele = (ids) => {
  return axios.delete("/api/Label/batchDelete?ids=" + ids).then(res => res.data)
};
/**
 * 评论管理
 **/
// 评论管理-获取评论列表
export const commentList = (params) => {
    return req("post", "/api/Comment/list", params)
};
// 评论管理-删除评论
export const commentDetele = (commentid) => {
    return axios.delete("/api/Comment/delete?commentid=" + commentid).then(res => res.data)
};
// 评论管理-批量删除评论
export const commentBatchDetele = (ids) => {
    return axios.delete("/api/Comment/batchDelete?ids=" + ids).then(res => res.data)
};
// 评论管理-审核状态修改
export const updateCommentAudit = (commentid, audit) => {
    return axios.post("/api/Comment/audit?commentid=" + commentid + "&audit=" + audit).then(res => res.data)
};

