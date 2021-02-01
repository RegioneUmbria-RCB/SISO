PrimeFaces.monitorDownload = function(start,complete)
{   
	var canReadCookies=PrimeFaces.getCookie("JSESSIONID")!==null;
	//alert('Canreadcookie: '+canReadCookies);
	if(this.cookiesEnabled() && canReadCookies ){

	if(start){
		start();
	}
	window.downloadMonitor=setInterval(function(){
			var downloadComplete=PrimeFaces.getCookie("primefaces.download");
			if(downloadComplete==="true"){
				if(complete){complete();}
					clearInterval(window.downloadMonitor);
					//alert('HO CHIUSO');
					PrimeFaces.setCookie("primefaces.download","");
			}
	},250);
	}

};

function start() {
    PF('statusDialog').show();
}
 
function stop() {
    PF('statusDialog').hide();
}

function startExport() {
    PF('statusDialogExport').show();
}
 
function stopExport() {
    PF('statusDialogExport').hide();
}
 