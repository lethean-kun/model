package com.uhetrip.api.dto;

import java.util.List;

import com.uhetrip.api.dto.common.PsgInfo;
import com.uhetrip.api.dto.common.UheReq;

public class UheCeateOrderReq extends UheReq {
    private String sessionid;
    private Param param;

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    public static class ContactInfo {

        private String name;
        private String address;
        private String postcode;
        private String email;
        private String mobile;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile() {
            return mobile;
        }

    }

    public static class Param {

        private String data;
        private List<PsgInfo> psgInfo;
        private ContactInfo contactInfo;

        public void setData(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public List<PsgInfo> getPsgInfo() {
            return psgInfo;
        }

        public void setPsgInfo(List<PsgInfo> psgInfo) {
            this.psgInfo = psgInfo;
        }

        public void setContactInfo(ContactInfo contactInfo) {
            this.contactInfo = contactInfo;
        }

        public ContactInfo getContactInfo() {
            return contactInfo;
        }

    }
}
