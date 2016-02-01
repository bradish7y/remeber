1.jdk proxy:
    被代理的类实现了某个接口
    强制使用cglib代理
    <aop:config proxy-target-class="true">
        <!-- other beans defined here... -->
    </aop:config>

    注解方式时：
    <aop:aspectj-autoproxy proxy-target-class="true"/>

2.cglib proxy