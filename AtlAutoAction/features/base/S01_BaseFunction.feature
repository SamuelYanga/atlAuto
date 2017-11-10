@all
Feature: login

Background:
	Given 打开主页。

@loginSuccess
Scenario Outline: ABO user login success
    And ABO用户登录. userId="<userId>" password="<password>"
    Then 验证成功登录后，页面头部显示用户名称。userName="<userName>"
    Examples:
    |userId			|password	|userName		|
    |2234518		|123456		|TestUser		|

@loginFail
Scenario Outline: ABO user login success
    And ABO用户登录. userId="<userId>" password="<password>"
    Then 验证过期用户登录后，页面头部显示续约提示信息。
    Examples:
    |userId			|password	|
    |1234513		|123456		|

@basefunction
Scenario Outline: ABO user login success
    And ABO用户登录（空购物车）. userId="<userId>" password="<password>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 点击mini购物车图标，进入购物车页面。
    And 验证购物车页面的产品信息。
    And 验证购物车页面的合计信息。
    And 点击结账按钮，进入结算页面。
    And 使用產品抵用券, 金额="<ticket1value>"
    And 使用e化現金代用券, 金额="<ticket2value>"
    And 使用現金抵用券, 金额="<ticket3value>"
    And 点击核对订单，进入订单核对页面。
    And 验证核对订单页面的产品信息。
    Then 点击送出订单，然后生成订单。
    Examples:
    |userId			|password	|productId	|productNum	|ticket1value	|ticket2value	|ticket3value	|
    |2234518		|123456		|2056		|2			|1				|1				|1				|

@removeAll@remove
Scenario Outline: ABO user login success
    And ABO用户登录. userId="<userId>" password="<password>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId1>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId2>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 点击mini购物车图标，进入购物车页面。
    And 验证购物车页面的产品信息。
    And 验证购物车页面的合计信息。
    And 点击整批删除。
    Then 验证整批删除成功。
    Examples:
    |userId			|password	|productId1	|productNum	|productId2	|
    |2234518		|123456		|2056		|2			|3059		|

@removeBatch@remove
Scenario Outline: ABO user login success
    And ABO用户登录（空购物车）. userId="<userId>" password="<password>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId1>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId2>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId3>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 点击mini购物车图标，进入购物车页面。
    And 验证购物车页面的产品信息。
    And 验证购物车页面的合计信息。
    And 选择部分产品，并删除。 productId1="<productId1>" productId2="<productId2>"
    Then 验证部分删除成功。 productId1="<productId1>" productId2="<productId2>"
    Examples:
    |userId			|password	|productId1	|productNum	|productId2	|productId3	|
    |2234518		|123456		|2056		|2			|3059		|3310		|

@removeOne@remove
Scenario Outline: ABO user login success
    And ABO用户登录（空购物车）. userId="<userId>" password="<password>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId1>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId2>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId3>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 点击mini购物车图标，进入购物车页面。
    And 验证购物车页面的产品信息。
    And 验证购物车页面的合计信息。
    And 删除一个产品。 productId="<productId1>"
    Then 验证删除产品成功。 productId="<productId1>"
    Examples:
    |userId			|password	|productId1	|productNum	|productId2	|productId3	|
    |2234518		|123456		|2056		|2			|3059		|3310		|

@promotion
Scenario Outline: ABO user login success
    And ABO用户登录（空购物车）. userId="<userId>" password="<password>"
    And 点击mini购物车图标，进入购物车页面。
    And 点击購物專區，选择特惠活動。
    And 根据活动编号，选择一个活动，点击立即参与。 promotionCode="<promotionCode>"
    And 选择活动相关产品，活动类型选择两种类型产品，可获赠礼品。 productId1="<productId1>" num1="<productNum>" productId2="<productId2>" num2="<productNum>"
    Then 验证满足条件后，活动礼品出现，并且选择产品的图片和数量正确显示。
    Examples:
    |userId			|password	|promotionCode	|productId1	|productNum	|productId2	|
    |2234518		|123456		|10016			|14			|2			|9			|

@changeLocation
Scenario Outline: ABO user login success
    And ABO用户登录（空购物车）. userId="<userId>" password="<password>"
    And 更换送货地点。 newLocation="<newLocation>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 点击mini购物车图标，进入购物车页面。
    And 验证送货方式和指定送货方式是否一致。 location="<newLocation>"
    And 点击结账按钮，进入结算页面。
    And 使用產品抵用券, 金额="<ticket1value>"
    And 使用e化現金代用券, 金额="<ticket2value>"
    And 使用現金抵用券, 金额="<ticket3value>"
    And 点击核对订单，进入订单核对页面。
    And 验证核对订单页面的产品信息。
    And 点击送出订单，然后生成订单。
    Examples:
    |userId			|password	|productId	|productNum	|ticket1value	|ticket2value	|ticket3value	|newLocation	|
    |2234518		|123456		|2056		|2			|1				|1				|1				|IEC體驗店1		|

@Eticket@menuUI
Scenario Outline: ABO user login success
    And ABO用户登录（空购物车）. userId="<userId>" password="<password>"
    And 搜索一个产品，然后点击进入产品详情页. productId="<productId>"
    And 调整产品数量，然后加入购物车。 productNum="<productNum>"
    And 点击mini购物车图标，进入购物车页面。
    And 点击结账按钮，进入结算页面。
    And 使用產品抵用券, 金额="<ticket1value>"
    And 使用e化現金代用券, 金额="<ticket2value>"
    And 使用現金抵用券, 金额="<ticket3value>"
    And 点击核对订单，进入订单核对页面。
    And 验证优惠券使用情况。 產品抵用券="<ticket1value>" e化現金代用券="<ticket2value>" 現金抵用券="<ticket3value>"
    And 点击送出订单，然后生成订单。
    Examples:
    |userId			|password	|productId	|productNum	|ticket1value	|ticket2value	|ticket3value	|
    |2234518		|123456		|2056		|2			|1				|1				|1				|

@menuUI
Scenario Outline: ABO user login success
    Then 验证header部的一二级菜单是否和主页左侧一二级菜单保持一致。
    Examples:
    ||
    ||
    