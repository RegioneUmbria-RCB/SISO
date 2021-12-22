
//	function putOverlayPanel(idElement){ 
//		var p = $( "#" + idElement );
//		var position = p.position();
// 		 alert("left: " + position.left + ", top: " + position.top +  "height: " + p.height() + ", width: " + p.width()   ); 
//		$('body').append($('<div class="overlay-panel"/>' )); 
//		$(".overlay-panel").height( (position.top + p.height()) );	 
//		$(".overlay-panel").width( $( window ).width() ); 
//	}
	

	function putPaiOverlayPanel(jqObject){  
		var position = jqObject.position();
//			alert("left: " + position.left + ", top: " + position.top +  "height: " + jqObject.height() + ", width: " + jqObject.width()   ); 
		$('body').append($('<div class="overlay-panel"/>' )); 
		$(".overlay-panel").height( (position.top + jqObject.children( ":first-child" ).height()) );	 
		$(".overlay-panel").width( $( window ).width() ); 
	}
	
	function removePaiOverlayPanel(){
		$( ".overlay-panel" ).remove();
	}