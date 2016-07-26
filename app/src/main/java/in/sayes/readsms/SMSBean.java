package in.sayes.readsms;

import java.util.List;

/**
 * Created by sourav on 16/06/16.
 */
public class SMSBean {

    /**
     * SMS_Body : test
     * Phone_No : 7878798777998
     * Date : 12/12/16
     */

    private List<SMSLISTBean> SMS_LIST;

    public List<SMSLISTBean> getSMS_LIST() {
        return SMS_LIST;
    }

    public void setSMS_LIST(List<SMSLISTBean> SMS_LIST) {
        this.SMS_LIST = SMS_LIST;
    }

    public static class SMSLISTBean {
        private String SMS_Body;
        private String Phone_No;
        private String Date;

        public String getSMS_Body() {
            return SMS_Body;
        }

        public void setSMS_Body(String SMS_Body) {
            this.SMS_Body = SMS_Body;
        }

        public String getPhone_No() {
            return Phone_No;
        }

        public void setPhone_No(String Phone_No) {
            this.Phone_No = Phone_No;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }
    }
}
