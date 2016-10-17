google.charts.load('current', {'packages':['line']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
  var data = new google.visualization.DataTable();
  data.addColumn('number', 'Temperature');
  data.addColumn('number', 'Temperature');

  data.addRows([
    [0,1],
    [5,2],
    [6,3]
  ]);

  var options = {
    chart: {
      title: 'Room Temperature',
    },
    width: 900,
    height: 500
  };


  var chart = new google.charts.Line(document.getElementById('chart_div'));

  chart.draw(data, options);
}