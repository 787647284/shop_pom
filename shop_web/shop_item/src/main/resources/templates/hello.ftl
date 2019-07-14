<html>
<head>
    <title>freemaker模板页面</title>
</head>
<body>
     hello,${key}<hr/>
     <#if age<18>
         未成年
         <#elseif age<50>
         成年
     <#elseif age<60>
         中年
         <#else >
         老年
     </#if>
     <hr/>
<#--    <#switch  >

    </#switch>-->
    <#list ages as age>
        ${age}
    </#list>
<hr/>
日期${date?date}
时间${date?time}
日期加时间${date?datetime}
${date?string("yyyy,MM,dd")}
</body>
</html>