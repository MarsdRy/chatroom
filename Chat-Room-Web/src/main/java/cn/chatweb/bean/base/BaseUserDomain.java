package cn.chatweb.bean.base;

import cn.chatweb.bean.BaseDomain;
import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.math.BigInteger;

/**
 * Created by 哲 on 2016/9/7.
 */
public abstract class BaseUserDomain<M extends BaseUserDomain<M>> extends BaseDomain<M> implements IBean{

    public static final String CACHE_NAME = "user";
    public static final String METADATA_TYPE = "user";

    public void removeCache(Object key){
        CacheKit.remove(CACHE_NAME, key);
    }

    public void putCache(Object key, Object value){
        CacheKit.put(CACHE_NAME, key, value);
    }

    public M getCache(Object key){
        return CacheKit.get(CACHE_NAME, key);
    }

    public M getCache(Object key, IDataLoader dataLoader){
        return CacheKit.get(CACHE_NAME, key, dataLoader);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }

        if(!(o instanceof BaseUserDomain<?>)){
            return false;
        }

        BaseUserDomain<?> m = (BaseUserDomain<?>) o;
        if(m.getId() == null){
            return false;
        }

        return m.getId().compareTo(this.getId()) == 0;
    }

    public void setId(BigInteger id){
        set("id", id);
    }

    public BigInteger getId(){
        Object id = get("id");
        if (id == null) {
            return null;
        }
        return id instanceof BigInteger ? (BigInteger)id : new BigInteger(id.toString());
    }

    public void setUsername(java.lang.String username) {
        set("username", username);
    }

    public java.lang.String getUsername() {
        return get("username");
    }

    public void setNickname(java.lang.String nickname) {
        set("nickname", nickname);
    }

    public java.lang.String getNickname() {
        return get("nickname");
    }

    public void setRealname(java.lang.String realname) {
        set("realname", realname);
    }

    public java.lang.String getRealname() {
        return get("realname");
    }

    public void setPassword(java.lang.String password) {
        set("password", password);
    }

    public java.lang.String getPassword() {
        return get("password");
    }

    public void setSalt(java.lang.String salt) {
        set("salt", salt);
    }

    public java.lang.String getSalt() {
        return get("salt");
    }

    public void setEmail(java.lang.String email) {
        set("email", email);
    }

    public java.lang.String getEmail() {
        return get("email");
    }

    public void setEmailStatus(java.lang.String emailStatus) {
        set("email_status", emailStatus);
    }

    public java.lang.String getEmailStatus() {
        return get("email_status");
    }

    public void setMobile(java.lang.String mobile) {
        set("mobile", mobile);
    }

    public java.lang.String getMobile() {
        return get("mobile");
    }

    public void setMobileStatus(java.lang.String mobileStatus) {
        set("mobile_status", mobileStatus);
    }

    public java.lang.String getMobileStatus() {
        return get("mobile_status");
    }

    public void setTelephone(java.lang.String telephone) {
        set("telephone", telephone);
    }

    public java.lang.String getTelephone() {
        return get("telephone");
    }

    public void setAmount(java.math.BigDecimal amount) {
        set("amount", amount);
    }

    public java.math.BigDecimal getAmount() {
        return get("amount");
    }

    public void setGender(java.lang.String gender) {
        set("gender", gender);
    }

    public java.lang.String getGender() {
        return get("gender");
    }

    public void setRole(java.lang.String role) {
        set("role", role);
    }

    public java.lang.String getRole() {
        return get("role");
    }

    public void setSignature(java.lang.String signature) {
        set("signature", signature);
    }

    public java.lang.String getSignature() {
        return get("signature");
    }

    public void setContentCount(java.lang.Long contentCount) {
        set("content_count", contentCount);
    }

    public java.lang.Long getContentCount() {
        return get("content_count");
    }

    public void setCommentCount(java.lang.Long commentCount) {
        set("comment_count", commentCount);
    }

    public java.lang.Long getCommentCount() {
        return get("comment_count");
    }

    public void setQq(java.lang.String qq) {
        set("qq", qq);
    }

    public java.lang.String getQq() {
        return get("qq");
    }

    public void setWechat(java.lang.String wechat) {
        set("wechat", wechat);
    }

    public java.lang.String getWechat() {
        return get("wechat");
    }

    public void setWeibo(java.lang.String weibo) {
        set("weibo", weibo);
    }

    public java.lang.String getWeibo() {
        return get("weibo");
    }

    public void setFacebook(java.lang.String facebook) {
        set("facebook", facebook);
    }

    public java.lang.String getFacebook() {
        return get("facebook");
    }

    public void setLinkedin(java.lang.String linkedin) {
        set("linkedin", linkedin);
    }

    public java.lang.String getLinkedin() {
        return get("linkedin");
    }

    public void setBirthday(java.util.Date birthday) {
        set("birthday", birthday);
    }

    public java.util.Date getBirthday() {
        return get("birthday");
    }

    public void setCompany(java.lang.String company) {
        set("company", company);
    }

    public java.lang.String getCompany() {
        return get("company");
    }

    public void setOccupation(java.lang.String occupation) {
        set("occupation", occupation);
    }

    public java.lang.String getOccupation() {
        return get("occupation");
    }

    public void setAddress(java.lang.String address) {
        set("address", address);
    }

    public java.lang.String getAddress() {
        return get("address");
    }

    public void setZipcode(java.lang.String zipcode) {
        set("zipcode", zipcode);
    }

    public java.lang.String getZipcode() {
        return get("zipcode");
    }

    public void setSite(java.lang.String site) {
        set("site", site);
    }

    public java.lang.String getSite() {
        return get("site");
    }

    public void setGraduateschool(java.lang.String graduateschool) {
        set("graduateschool", graduateschool);
    }

    public java.lang.String getGraduateschool() {
        return get("graduateschool");
    }

    public void setEducation(java.lang.String education) {
        set("education", education);
    }

    public java.lang.String getEducation() {
        return get("education");
    }

    public void setAvatar(java.lang.String avatar) {
        set("avatar", avatar);
    }

    public java.lang.String getAvatar() {
        return get("avatar");
    }

    public void setIdcardtype(java.lang.String idcardtype) {
        set("idcardtype", idcardtype);
    }

    public java.lang.String getIdcardtype() {
        return get("idcardtype");
    }

    public void setIdcard(java.lang.String idcard) {
        set("idcard", idcard);
    }

    public java.lang.String getIdcard() {
        return get("idcard");
    }

    public void setStatus(java.lang.String status) {
        set("status", status);
    }

    public java.lang.String getStatus() {
        return get("status");
    }

    public void setCreated(java.util.Date created) {
        set("created", created);
    }

    public java.util.Date getCreated() {
        return get("created");
    }

    public void setCreateSource(java.lang.String createSource) {
        set("create_source", createSource);
    }

    public java.lang.String getCreateSource() {
        return get("create_source");
    }

    public void setLogged(java.util.Date logged) {
        set("logged", logged);
    }

    public java.util.Date getLogged() {
        return get("logged");
    }

    public void setActivated(java.util.Date activated) {
        set("activated", activated);
    }

    public java.util.Date getActivated() {
        return get("activated");
    }


}
