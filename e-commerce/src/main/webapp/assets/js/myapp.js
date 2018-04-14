$(function() {
	//solving active menu problem 
	switch(menu){
		case 'About Us':
			$('#about').addClass('active');
			break;
		case 'Contact Us':
			$('#contact').addClass('active');
			break;
		case 'All Products':
			$('#listProducts').addClass('active');
			break;
		case 'Manage Products':
			$('#manageProducts').addClass('active');
			break;
		default:
			if(menu == "Home") break;
			$('#listProducts').addClass('active');
			$('#a_'+menu).addClass('active');
			break;
			
	}
	
	
		//code for jquery dataTable

	var $table = $('#productListTable')
	
	//EXTCUTE THIS TABLE ONLY IF BELOW CODE IS AVAKIABLE
	
	if($table.length){		
		//console.log('Inside tab;e')
		
		var jsonUrl = '';
		if (window.categoryId == '') {
			jsonUrl = window.contextRoot + '/json/data/all/products';
		} else {
			jsonUrl = window.contextRoot + '/json/data/category/'
					+ window.categoryId + '/products';
		}
		
		$table.DataTable({
			lengthMenu: [[3,5,10,-1],['3 Records','5 Records','10 Records','All Records']],
			pageLength:5,
			
			ajax: {
				url: jsonUrl,
				dataSrc: ''
			},
			columns: [
						{
							data : 'code',
							bSortable : false,
							mRender : function(data, type, row) {

								return '<img src="' + window.contextRoot
								+ '/resources/images/' + data
								+ '.jpg" class="dataTableImg"/>';
								
							}
						},
						{
							data: 'name'					
						},
						{
							data: 'brand'					
						},
						{
							data: 'unitPrice',
							mRender: function(data, type, row){
								return '&#8377; ' + data
							}
						},
						{
							data: 'quantity'					
						},
						{
							data: 'id',
							bSortable: false,
							mRender: function(data, type, row){
									var str = '';
									str += '<a href="'+ window.contextRoot+ '/show/'+ data + '/product" class="btn btn-primary"><span class="glyphicon glyphicon-eye-open"></span></a> &#160;';		
									str += '<a href="'+ window.contextRoot+ '/cart/add/'+ data + '/product class="btn btn-success"><span class="glyphicon glyphicon-shopping-cart "></span></a>';	
									return str;
								}
							
						}
				
					] 
			
		});
	}
	
	// dissmiing the alert after 3 seconds
	var $alert=$('.alert');
	
	if($alert.length){
		setTimeout(function(){
			$alert.fadeOut('slow'); 
		},3000)
	}
	// -------------------------
	
	$('.switch input[type="checkbox"]').on('change',function(){
		
		var checkbox = $(this);
		var checked = checkbox.prop('checked');
		var dMsg = (checked)? 'You want to activate the product?':
							  'You want to deactivate the product?';
		var value= checkbox.prop('value');
		
		bootbox.confirm({
			size: 'medium',
	    	title: 'Product Activation/Deactivation',
	    	message: dMsg,
	    	callback: function(confirmed){
	    		
	    		if(confirmed){
	    			console.log(value);
	    			bootbox.alert({
	    				size: 'medium',
	    		    	title: 'Information',
	    		    	message: 'You are going to perform operation on product ' + value
	    			});
	    		}
	    		else{
	    			
	    			checkbox.prop("checked",!checked);
	    		}
	    	} 
		});
		});	
	
	//data table for admin
	
var $adminProductsTable = $('#productsTable')
	
	//EXTCUTE THIS TABLE ONLY IF BELOW CODE IS AVAKIABLE
	
	if($adminProductsTable.length){		
		//console.log('Inside tab;e')
		
		var jsonUrl = window.contextRoot + '/json/data/admin/all/products';
		
		$adminProductsTable.DataTable({
			lengthMenu: [[10,30,50,-1],['10 Records','30 Records','50 Records','All']],
			pageLength:30,
			
			ajax: {
				url: jsonUrl,
				dataSrc: ''
			},
			columns: [
				
						{
							data:'id'
						},
						{
							data : 'code',
							bSortable : false,
							mRender : function(data, type, row) {

								return '<img src="' + window.contextRoot
								+ '/resources/images/' + data
								+ '.jpg" class="adminDataTableImg"/>';
								
							}
						},
						{
							data: 'name'					
						},
						{
							data: 'brand'					
						},						
						{
							data: 'quantity',
							mRender:function(data,type,row){
								if(data < 1) {
									return '<span styles="color:red">Out Of Stock</span>';
								}
								return data;								
							}
						},
						{
							data: 'unitPrice',
							mRender: function(data, type, row){
								return '&#8377; ' + data
							}
						},
						{
							data: 'active',	
							bSortable:false,
							mRender:function(data,type,row){
								
								var str='';
								str += '<label class="switch">';
								if(data) {
									str += '<input type="checkbox" checked="checked" value="'+row.id+'"/>';
 								}
								else {
									str += '<input type="checkbox" value="'+row.id+'"/>';
								}
								str += '<div class="slider"></div> </label>';
								return str;
							}
						},
						{
							data:'id',
							bSortable:false,
							mRender:function(data,type,row){
								var str='';
								str += '<a href="'+window.contextRoot+'/manage/'+ data +'/product" class="btn btn-working">';
								str +=	'<span class="glyphicon glyphicon-pencil"></span> </a>';
								return str;
									
							}
						}
				
					],
					initComplete:function(){
						var api =  this.api();
						api.$('.switch input[type="checkbox"]').on('change',function(){
							
							var checkbox = $(this);
							var checked = checkbox.prop('checked');
							var dMsg = (checked)? 'You want to activate the product?':
												  'You want to deactivate the product?';
							var value= checkbox.prop('value');
							
							bootbox.confirm({
								size: 'medium',
						    	title: 'Product Activation/Deactivation',
						    	message: dMsg,
						    	callback: function(confirmed){
						    		
						    		if(confirmed){
						    			if (confirmed) {
								            $.ajax({							            	
								            	type: 'POST',
								            	url: window.contextRoot + '/manage/product/'+checkbox.prop('value')+'/activation',
								        		timeout : 100000,
								        		success : function(data) {
								        			bootbox.alert(data);							        										        			
								        		},
								        		error : function(e) {
								        			bootbox.alert('ERROR: '+ e);
								        			display(e);
								        		}						            	
								            });
								        }
						    		}
						    		else{
						    			
						    			checkbox.prop("checked",!checked);
						    		}
						    	} 
							});
							});	
					}
			
		});
	}
	
	//-------------------------------
//validate code for category
var $categoryForm = $('#categoryForm');
if($categoryForm.length){
	$categoryForm.validate({
		rules: {
			
			name: {
				
				required:true,
				minlength:2
			},
			
			description:{
				required:true
				}			
			},
			messages : {
				name:{
					required:"Please add the category name",
					minlength:'The category name should not be less than 2 characters'
				},
				description:{
					required:"Please add the Decription for this category"
				}
			},
			
			errorElement: 'em',
			errorPlacement:function(error,element){
				//add the class of help block
				error.addClass('help-block');
				//add the error element AFTER input element 
				error.insertAfter(element);
			}
	});
	//////!!!!!!!!!!!!!!!!!!!
}






});