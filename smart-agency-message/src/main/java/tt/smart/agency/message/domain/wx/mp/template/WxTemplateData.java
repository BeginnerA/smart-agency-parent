package tt.smart.agency.message.domain.wx.mp.template;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 微信公众号模板消息数据
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WxTemplateData implements Serializable {

    /**
     * 模板消息 key
     */
    private String name;

    /**
     * 模板消息值
     */
    private String value;

    /**
     * 获取模板消息数据 JsonObject
     *
     * @param dataList 模板消息数据列表
     * @return 模板消息数据 JsonObject
     */
    public static JSONObject getJsonObject(List<WxTemplateData> dataList) {
        JSONObject data = new JSONObject();
        for (WxTemplateData datum : dataList) {
            JSONObject dataJson = new JSONObject();
            dataJson.put("value", datum.getValue());
            data.put(datum.getName(), dataJson);
        }
        return data;
    }

}
