/*
 * belk.pep.editimage.js
 * Belk CARS - Edit image javascript
 * -rlr
 * Copyright Belk, Inc.
 */
belk.pep.editimage={
	init:function(){
		var EC=belk.pep.editimage;
		EC._setupNotes();	
		EC._setupMaxCharsPlugin();
		EC._setupMaxChars();	
		EC._setupSpellcheck();	
		EC._setupDropDownShow();
		EC._showTextCounter();		
		$(function(){
		  var target = location.hash && $(location.hash)[0];
		  if( target )
		      $.scrollTo( target, {speed:1000} );
		}); 
	},
	// private methods
	_setupNotes:function(){
		// Add Note functionality
		var setupNotes=function(btnSel,winId,contentId,notesULSel,btnTxt,msgSel,msg){
			var win=null;
			$(btnSel).click(function(){
			
				// show add sample image note window when add note button clicked
				$(this).blur();
				if (!win) {
					win = new Ext.Window({
						el:winId,
						layout:'fit',
						width:350,
						autoHeight:true,
						closeAction:'hide',
						modal:true,
						plain:true,
						items: new Ext.Panel({
		                    contentEl:contentId,
		                    deferredRender:false,
		                    border:false,
							autoHeight:true
		                }),
						buttons: [{
				                text: 'Cancel',
				                handler: function(){
				                    win.hide();
				                }
			            	},{
				                text: btnTxt,
				                handler: function(){
									
									var html=[];
												var notes=$("#sampleNotesMessageId").val();
												var missing=false;
												if(notes===''){
														missing=true;
												}
												if(missing){
												Ext.MessageBox.show({
										           title: 'Required fields missing',
										           msg: 'Please enter a note to add.',
										           buttons: Ext.MessageBox.OK,
										           icon: 'ext-mb-warning'
										       	});
												return false;
											}											
												
												var date = new Date();
												var datestring = ("0" + (date.getMonth() + 1).toString()).substr(-2) + "/" + ("0" + date.getDate().toString()).substr(-2)  + "/" + (date.getFullYear().toString());

												var l=notes.length;
												alert(' l -- >'+l);
												//for(var i=0;i<=l-1;i++){
													html.push('<li><ul><li class="date"><strong>Date:</strong> ',datestring);
													html.push('</li><li class="name"><strong>Name:</strong> ',"pepadmin@Belk.com");
													html.push('</li><li class="text">',notes,'</li></ul></li>');
												//}
												$(notesULSel).html(html.join(''));
												win.hide();
														
										
										
//alert('text '+text);										
									// add note using ajax submit
								/*	$('#'+contentId+' form').ajaxSubmit({
										dataType:'json',
										success: function(resp){
												alert('hi sadfasdsss');
											if(resp.success&&resp.notes_data&&resp.notes_data.length){
											
												var html=[];
												var notes=resp.notes_data;
												var l=notes.length;
												for(var i=0;i<=l-1;i++){
													html.push('<li><ul><li class="date"><strong>Date:</strong> ',notes[i][2]);
													html.push('</li><li class="name"><strong>Name:</strong> ',notes[i][3]);
													html.push('</li><li class="text">',notes[i][1],'</li></ul></li>');
												}
												$(notesULSel).html(html.join(''));
												win.hide();
												$(msgSel).html(msg).fadeIn(function(){
													var $this=$(this);
													setTimeout(function(){
														$this.fadeOut("slow");
													},3000);
												});
												$('#'+contentId+' textarea').val('').maxCharsReset();
											}
											else{
												Ext.MessageBox.show({
										           title: 'Error',
										           msg: 'Oops!. There was an error, please try again.',
										           buttons: Ext.MessageBox.OK,
										           icon: 'ext-mb-error'
										       	});
											}
										},
										beforeSubmit:function(){
											// validation before submitting
											var $form=$('#'+contentId+' form');
											$('textarea.error',$form).removeClass('error'); //clear existing errors
											var missing=false;
											$('textarea.note_text',$form).each(function(){
												var $this=$(this);
												if($this.val()===''){
													missing=true;
													$this.addClass('error');
												}
											});
											if(missing){
												Ext.MessageBox.show({
										           title: 'Required fields missing',
										           msg: 'Please enter a note to add.',
										           buttons: Ext.MessageBox.OK,
										           icon: 'ext-mb-warning'
										       	});
												return false;
											}
											return true;
										},
										url:$('#'+contentId+' form').attr('action')+'?ajax=true'
									});*/
				                }
							}
						]
					});
				}
		        win.show(function(){
					$('#add_car_note_form textarea.note_text').focus();
				});
				return false;
			});
		};
		
		//sample notes
		setupNotes('#add_sample_note_btn','add_sample_note_win','add_sample_note_form','ul.sample_notes','Add Sample Note','#add_sample_note_msg','Sample Note added successfully!');
		//return notes
		setupNotes('#add_return_note_btn','add_return_note_win','add_return_note_form','ul.return_notes','Add Return Note','#add_sample_note_msg','Return Note added successfully!');
		
		
	},
	
	_setupMaxCharsPlugin:function(){
		// custom jquery plugin for the max/remaining characters functionality
		jQuery.fn.maxChars = function(){
			return this
			.keydown(function(ev){
				// check max chars
				var $this=$(this);
				var max=parseInt($('span.max_chars_val',$this.parent()).html());
				if($this.val().length>=max && ev.keyCode!==8 && ev.keyCode!==46){
					if($this.val().length>max){
						$this.val($this.val().substring(0,max-1));
					}
					return false;
				}
				return true;
			});
		};
		jQuery.fn.maxCharsReset = function(){
			return this.each(function(){
				/*var $this=$(this);
				var max=parseInt($('span.max_chars_val',$this.parent()).html());
				$('span.chars_rem_val',$this.parent()).html(max-$this.val().length||'0');*/
			});
		};
	},
	_setupMaxChars:function(){
		$('input.maxChars,textarea.maxChars').maxChars();
	},
	
	
	
	_setupSpellcheck:function(){
		$('textarea.spellcheck').each(function(){
			var $this=$(this);
			var googie = new GoogieSpell("googiespell/", context+"spellCheck.jsp?lang=");
			googie.decorateTextarea($this.attr('id'));
		});
	},
	_eatEnter:function(){
		$('input[type="text"]').keydown(function(ev){
			if(ev.keyCode===13){
				return false;
			}
			return true;
		});
	},


	_showTextCounter:function(){
			var total = ($('#txt_outfitName').length > 0)? $('#txt_outfitName').val().length : 0;
			$('#outfit_name_length').text(total);	
			var prdtotal = $('.textCount').val().length;
			$('#product_desc_length').text(prdtotal);

		},	


};


//Escape all HTML encoded ampersand characters with '&' from outfitName
function escapeChars(outfitName) {
 	if (outfitName.indexOf("&amp;") >= 0) {
 		outfitName= outfitName.replace(/\&amp;/g,'&');
	}
 	return outfitName;
}



var idArray = new Array();
$("input[id^='parentCarId']").each(function(){
	idArray.push($(this).val());
});
function setID()
{
	for (var i = 0; i < idArray.length; i++) 
	{
		$('.child_'+i).find('.x-panel-header').html('Child Car Id : '+idArray[i]);
	}
}
setID();
//dom is ready...
$(document).ready(belk.pep.editimage.init);

//code for completion date
window.onload = function() {
var shipDate= $('#shipmentupdateddate').val();
var shipmentupdateddate = Date.parseDate(shipDate,"Y-m-d");
var currentDate= new Date();
shipmentupdateddate.setMonth(shipmentupdateddate.getMonth()+3);
if(currentDate<shipmentupdateddate){
$('.completion_date').css("color","red");
}
}