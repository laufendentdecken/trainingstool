<#import "masterTemplate.ftl" as layout />

<@layout.masterTemplate title="Überblick">
<div id="calendar-wrap">
    <header>
        <h1><a href="/app/welcome?period=${previous}"> << </a> ${currentPeriod} <a href="/app/welcome?period=${next}"> >> </a></h1>
    </header>
    <div id="calendar">
        <ul class="weekdays">
        	<#list weekdays as day>
            	<li>${day}</li>
            </#list>
        </ul>

        <!-- Days from previous month -->
		<#list period as week>
        <ul class="days">
        	<#list week.weekdays as day>
            <li class="day <#if day.otherMonth> other-month </#if>">
                <div class="date">${day.day}</div>
                <#if day.hasEvent>
	                <div class="event">
	                    <div class="${day.type}">
	                        ${day.description}
	                    </div>
	                </div>
	           </#if>                               
            </li>
            </#list>
        </ul>
        </#list>

    </div><!-- /. calendar -->
</div><!-- /. wrap -->   
</@layout.masterTemplate>