$('document').ready(function() {
	$('a#prephase_visible').click(function() {
		$('#prephase').toggle();
	});
	
	$('a.removeFromArea').click(function(e){
		e.preventDefault();
		$(this).closest('form').submit();
	});
	
//	$('#add-area-link').click(function() {
//		$('#add-area').slideToggle();
//	});
});
