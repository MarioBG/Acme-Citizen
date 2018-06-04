/**
 * Add a 50% slice
 */
function makeVoteDiagram(chartData) {

//	var chartData = [
//			{
//				"party" : "Avanza - Por Hisperia",
//				"votes" : 11
//			}, {
//				"party" : "PIS",
//				"votes" : 3
//			}
//	];

	var sum = 0;
	for ( var x in chartData) {
		sum += chartData[x].votes;
	}
	chartData.push({
		"votes" : sum,
		"alpha" : 0
	});

	/**
	 * Create the chart
	 */

	var chart = AmCharts.makeChart("chartdiv", {
		"type" : "pie",
		"startAngle" : 0,
		"radius" : "90%",
		"innerRadius" : "50%",
		"dataProvider" : chartData,
		"valueField" : "votes",
		"titleField" : "party",
		"alphaField" : "alpha",
		"labelsEnabled" : false,
		"pullOutRadius" : 0,
		"pieY" : "95%"
	});
	return chart;
}
