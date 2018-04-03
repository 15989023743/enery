		function article_pv(articleId){
			articleService.pv(articleId,function(data) {
				//$('#articlepvid').html(data);
			});
		}