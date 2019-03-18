package me.clayclaw.bukkit.vip;

import org.bukkit.ChatColor;

public enum BuiltinMessage {

    ZH_TW_WELCOME("&2ClawVIP已成功載入，感謝你的使用"),
    ZH_CN_WELCOME("&2ClawVIP已成功载入，感谢你的使用"),

    ZH_TW_RELOADED("&2[ClawVIP] 已重新讀取設定檔"),
    ZH_CN_RELOADED("&2[ClawVIP] 已重新读取设定档"),

    ZH_TW_CREATELANGFILE("&2創建語言文檔"),
    ZH_CN_CREATELANGFILE("&2创建语言文档"),

    ZH_TW_FILENOTFOUND("&c缺漏語言文檔: "),
    ZH_CN_FILENOTFOUND("&c缺漏语言文档: "),

    ZH_TW_STRNOTFOUND("語言文件缺漏字串: "),
    ZH_CN_STRNOTFOUND("&c语言文件缺漏字串: "),

    ZH_TW_STRLISTNOTFOUND("&c語言文件缺漏字串列"),
    ZH_CN_STRLISTNOTFOUND("&c语言文件缺漏字串列: "),

    ZH_TW_NO("無"),
    ZH_CN_NO("无"),

    ZH_TW_ENTERREDEEMCODENAME("&c[ClawVIP] 請輸入序號設定名稱"),
    ZH_CN_ENTERREDEEMCODENAME("&c[ClawVIP] 请输入序号设定名称"),

    ZH_TW_REDEEMCODENOTFOUND("&c[ClawVIP] 使用序號失敗，沒有找到相關序號"),
    ZH_CN_REDEEMCODENOTFOUND("&c[ClawVIP] 使用序号失败，没有找到相关序号"),

    ZH_TW_REDEEMCODESETTINGSNOTFOUND("&c[ClawVIP] 使用序號失敗，此序號沒有找到相關序號設定"),
    ZH_CN_REDEEMCODESETTINGSNOTFOUND("&c[ClawVIP] 使用序号失败，此序号没有找到相关序号设定"),

    ZH_TW_REDEEMCODEVIPGROUPNOTFOUND("&c[ClawVIP] 使用序號失敗，此序號沒有找到相應VIP組"),
    ZH_CN_REDEEMCODEVIPGROUPNOTFOUND("&c[ClawVIP] 使用序号失败，此序号没有找到相关VIP组"),

    ZH_TW_SUCCESSFULLYREDEEM("&6[ClawVIP] 成功生成序號: "),
    ZH_CN_SUCCESSFULLYREDEEM("&6[ClawVIP] 成功生成序号: "),

    ZH_TW_TYPEREDEEMCODEREMINDER("&c[ClawVIP] 請先輸入序號"),
    ZH_CN_TYPEREDEEMCODEREMINDER("&c[ClawVIP] 请先输入序号"),

    ZH_TW_NONEXISTPLAYER("&c[ClawVIP] 該玩家沒有上線或不存在"),
    ZH_CN_NONEXISTPLAYER("&c[ClawVIP] 该玩家不存在或者没有上线"),

    ZH_TW_TYPEPLAYERNAMEREMINDER("&c[ClawVIP] 請先輸入玩家的名字"),
    ZH_CN_TYPEPLAYERNAMEREMINDER("&c[ClawVIP] 请先输入玩家的名字"),

    ZH_TW_PLAYERNOTVIP("&c[ClawVIP] 該玩家不是VIP"),
    ZH_CN_PLAYERNOTVIP("&c[ClawVIP] 该玩家不是VIP"),

    ZH_TW_VIPGROUPNOTFOUND("&c[ClawVIP] 沒有找到相關VIP組"),
    ZH_CN_VIPGROUPNOTFOUND("&c[ClawVIP] 没有找到相关VIP组"),

    ZH_TW_ENTERCORRECTNUMBER("&c[ClawVIP] 請輸入數字"),
    ZH_CN_ENTERCORRECTNUMBER("&c[ClawVIP] 请输入正确的数字"),

    ZH_TW_SUCCESSFULLYGIVE("&6[ClawVIP] 成功給予玩家 "),
    ZH_CN_SUCCESSFULLYGIVE("&6[ClawVIP] 成功给予玩家 "),

    ZH_TW_SUCCESSFULLYREMOVE("&6[ClawVIP] 成功移除"),
    ZH_CN_SUCCESSFULLYREMOVE("&6[ClawVIP] 成功移除"),

    ZH_TW_SVIPGROUP("的VIP用户組"),
    ZH_CN_SVIPGROUP("的VIP用户组"),

    ZH_TW_PERMANENT("永久"),
    ZH_CN_PERMANENT("永久"),

    ZH_TW_DAY("天"),
    ZH_CN_DAY("天"),

    ZH_TW_CANNOTGIVELOWTIER("&c[ClawVIP] 你不能給予玩家更低等級的VIP"),
    ZH_CN_CANNOTGIVELOWTIER("&c[ClawVIP] 你不能给予玩家更低等级的VIP"),

    ZH_TW_GIVECMDREMINDER("&c[ClawVIP] 請先輸入玩家名字、VIP用戶組、給予時間(按日計算)"),
    ZH_CN_GIVECMDREMINDER("&c[ClawVIP] 请先输入玩家名字、VIP用户组、给予时间(按日计算)"),

    ZH_TW_DEVELOPER("&6插件開發者: "),
    ZH_CN_DEVELOPER("&6插件开发人员: "),

    ZH_TW_PLUGINNAME("&6插件名稱: "),
    ZH_CN_PLUGINNAME("&6插件名称: "),

    ZH_TW_PLUGINVERSION("&6插件版本: "),
    ZH_CN_PLUGINVERSION("&6插件版本: "),

    ZH_TW_ONLINELIST("&6[ClawVIP] 在線VIP列表:"),
    ZH_CN_ONLINELIST("&6[ClawVIP] 在线VIP列表:"),

    ZH_TW_CONNECTINGTOMYSQL("&6[ClawVIP] 正在連接MySQL"),
    ZH_CN_CONNECTINGTOMYSQL("&6[ClawVIP] 正在连接MySQL"),

    ZH_TW_CONNECTFAILED("&c[ClawVIP] MySQL連接失敗"),
    ZH_CN_CONNECTFAILED("&c[ClawVIP] MySQL连接失败"),

    ZH_TW_TRYINGTODISCONNECT("&6[ClawVIP] 正在關閉對MySQL的連接"),
    ZH_CN_TRYINGTODISCONNECT("&6[ClawVIP] 正在关闭对MySQL的连接"),

    ZH_TW_FAILEDTODISCONNECT("&c[ClawVIP] 關閉MySQL連接失敗"),
    ZH_CN_FAILEDTODISCONNECT("&c[ClawVIP] 关闭MySQL连接失败"),

    ZH_TW_CONVERTERROR("&c轉換時發生錯誤 ID:"),
    ZH_CN_COVERTERROR("&c转换时发生错误 ID:"),
    EN_US_CONVERTERROR("&cConvert Error, ID:");

    String message;
    BuiltinMessage(String s){
        this.message = s;
    }

    public String getMsg(){ return message; }

    public static String getMessage(String id){
        return ChatColor.translateAlternateColorCodes('&',
                BuiltinMessage.valueOf(ClawVIP.language.toUpperCase() + "_" + id.toUpperCase()).getMsg());
    }

}
