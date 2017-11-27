<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<html>
<script type="text/javascript" src="${ctx}/resources/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-2.1.4.js"></script>
<script type="text/javascript">
    jQuery(document).ready(function() {

        var options = {
            chart: {
                renderTo: 'container',
                type: 'column'
            },
            title: {
                text: 'Tiny Tool Monthly Sales'
            },
            subtitle: {
                text: '2014 Q1-Q2'
            },
            xAxis: {
                categories: []
            },
            yAxis: {
                title: {
                    text: 'Sales'
                }
            },
            series: []
        };
        // JQuery function to process the csv data
        $.get('${ctx}/resources/js/column-data.csv', function(data) {
            // Split the lines
            var lines = data.split('\n');
            $.each(lines, function(lineNo, line) {
                var items = line.split(',');

                // header line contains names of categories
                if (lineNo == 0) {
                    $.each(items, function(itemNo, item) {
                        //skip first item of first line
                        if (itemNo > 0) options.xAxis.categories.push(item);
                    });
                }

                // the rest of the lines contain data with their name in the first position
                else {
                    var series = {
                        data: []
                    };
                    $.each(items, function(itemNo, item) {
                        if (itemNo == 0) {
                            series.name = item;
                        } else {
                            series.data.push(parseFloat(item));
                        }
                    });

                    options.series.push(series);

                }

            });
            //putting all together and create the chart
            var chart = new Highcharts.Chart(options);
        });

    });
</script>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Hello World</title>
</head>
<body>
"Hello World!"
<div id="container" style="width: 600px; height: 400px; margin: 0 auto"></div>
</body>
</html>