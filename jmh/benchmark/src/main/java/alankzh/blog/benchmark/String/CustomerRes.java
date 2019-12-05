package alankzh.blog.benchmark.String;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerRes {

    private String customerId;
    private String name;
    private String identityNumber;

    public String toStringJoinWithPlus(){
        String str =  "CustomerRes[";
        if (customerId != null) str += " customerId=" + customerId;
        if (name != null) str += ", name=" + name;
        if (identityNumber != null) str += ", identityNumber=" + identityNumber;
        str +="]";
        return str;
    }

    public String toStringJoinWithBuilder(){
        StringBuilder strBder = new StringBuilder("CustomerRes[");
        if (customerId != null) strBder.append(" customerId=").append(customerId);
        if (name != null) strBder.append(", name=").append(name);
        if (identityNumber != null) strBder.append(", identityNumber=").append(identityNumber);
        strBder.append("]");
        return strBder.toString();
    }

    public String toStringJoinWithPlusMask(){
        String str =  "CustomerRes[";
        if (customerId != null) str += " customerId=" + customerId;
        if (name != null) str += ", name=" + DisplayUtil.displayName(name);
        if (identityNumber != null) str += ", identityNumber=" + DisplayUtil.displayIdentityNumber(identityNumber);
        str +="]";
        return str;
    }

    public String toStringJoinWithBuilderMask(){
        StringBuilder strBder = new StringBuilder("CustomerRes[");
        if (customerId != null) strBder.append(" customerId=").append(customerId);
        if (name != null) strBder.append(", name=").append(DisplayUtil.displayName(name));
        if (identityNumber != null) strBder.append(", identityNumber=").append(DisplayUtil.displayIdentityNumber(identityNumber));
        strBder.append("]");
        return strBder.toString();
    }


    public static class DisplayUtil{
        public static String displayName(String name){
            if (name == null || name.length() <= 1){
                return name;
            }

            StringBuilder strbder = new StringBuilder();
            for (int i=0; i<name.length(); i++){
                if (i==0){
                    strbder.append(name.substring(i, i+1));
                } else {
                    strbder.append("*");
                }
            }
            return strbder.toString();
        }

        public static String displayIdentityNumber(String identityNumber){
            if (identityNumber == null || identityNumber.length() <= 4){
                return identityNumber;
            }

            StringBuilder strBder = new StringBuilder();
            for (int i=0; i<identityNumber.length(); i++){
                if(i < 4){
                    strBder.append(identityNumber.substring(i, i+1));
                } else {
                    strBder.append("*");
                }
            }
            return strBder.toString();
        }
    }

}
