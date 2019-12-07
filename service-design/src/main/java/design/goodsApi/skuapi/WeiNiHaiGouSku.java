package design.goodsApi.skuapi;

import lombok.Data;

import java.math.BigDecimal;

/**  维尼海购的sku 以他们api为准
 * User: xuxianbei
 * Date: 2019/11/13
 * Time: 9:55
 * Version:V1.0
 */
@Data
public class WeiNiHaiGouSku {
    /**
     * 商品编码
     */
    private String SkuNo;
    /**
     * 商品名称
     */
    private String SkuName;
    /**
     * 商品条码
     */
    private String BarCode;

    /**
     * 结算价大于0 单位：元
     */
    private BigDecimal SettlePrice;

    /**
     * 市场参考价大于0 单位：元
     */
    private BigDecimal RetailPrice;

    /**
     * 品牌
     */
    private String Brand;

    /**
     * 国别
     */
    private String Country;

    /**
     * 一级分类
     */
    private String Category;

    /**
     * 二级分类
     */
    private String TwoCategory;

    /**
     * 三级分类
     */
    private String ThreeCategory;

    /**
     * 详情介绍
     */
    private String Details;

    /**
     * 税率，非保税区这个字段为0
     */
    private BigDecimal Rate;

    /**
     * 发货方式
     * 1-保税区发货
     * 2-香港直邮
     * 4-海外直邮
     * 5-国内发货
     */
    private Integer DeliveryCode;

    /**
     * 销售类型
     * 0-批发价
     * 1-包邮包税价
     */
    private Integer SaleType;

    /**
     * 商品重量大于0 单位：克
     */
    private Integer Weight;

    /**
     * 商品主图（多个图片路径以分号隔开）
     */
    private String displayImgUrls;

    /**
     * 商品详情图（多个图片路径以分号隔开）
     */
    private String detailImgUrls;

    /**
     * 发货地
     */
    private String DeliveryCity;

    /**
     * 标品编码
     */
    private String goodsNo;

    /**
     * 商品规格
     */
    private String Spec;

    /**
     * 限购数量(0表示不限购)
     */
    private Integer LimitNum;

    /**
     * 过期时间
     */
    private String ValidDay;

    /**
     * 是否控价,true-是，false-否
     */
    private Boolean IsLimitPrice;
}
