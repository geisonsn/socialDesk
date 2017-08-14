$(function() {
	$("#new").focus();
	UsuarioProxy.findAll().done(findAllOk);

	MetadataProxy.getDemoiselleVersion().done(function(data) {
		$("#demoiselle-version").html(data);
	});

	$("form").submit(function(event) {
		event.preventDefault();
	});

	$("#new").click(function() {
		location.href = "usuario-edit.html";
	});

	$("#delete").click(function() {
		var ids = [];

		$("input:checked").each(function(index, value) {
			ids.push($(value).val());
		});

		if (ids.length == 0) {
			bootbox.alert({
				message : "Nenhum registro selecionado"
			});
		} else {
			bootbox.confirm("Tem certeza que deseja apagar?", function(result) {
				if (result) {
					UsuarioProxy.remove(ids).done(removeOk);
				}
			});
		}
	});
});

function findAllOk(data) {
	$('#resultList').dataTable({
		"aoColumns" : [ {
			"aTargets" : [ 0 ],
			"mDataProp" : "id",
			"mRender" : function(data, type, full) {
				return '<a href="usuario-edit.html?id=' + full.id + '">' + full.id + '</a>';
			}
		}, {
			"aTargets" : [ 1 ],
			"mDataProp" : "nomeUsuario",
			"mRender" : function(data, type, full) {
				return '<a href="usuario-edit.html?id=' + full.id + '">' + full.nomeUsuario + '</a>';
			}
		}, {
			"aTargets" : [ 1 ],
			"mDataProp" : "sobrenome",
			"mRender" : function(data, type, full) {
				return '<a href="usuario-edit.html?id=' + full.id + '">' + full.sobrenome + '</a>';
			}
		}, {
			"aTargets" : [ 2 ],
			"mDataProp" : "email",
			"mRender" : function(data, type, full) {
				return '<a href="usuario-edit.html?id=' + full.id + '">' + full.email + '</a>';
			}
		} ],
		"oLanguage" : {
			"sInfo" : "Mostrando _START_ a _END_ de _TOTAL_ registros",
			"sEmptyTable" : "Não há dados disponíveis na tabela",
			"sLengthMenu" : "Mostrar _MENU_ registros",
			"sInfoThousands" : "",
			"oPaginate" : {
				"sFirst" : "Primeiro",
				"sLast" : "Último",
				"sNext" : "Próximo",
				"sPrevious" : "Anterior"
			}
		},
		"bFilter" : false,
		"bDestroy" : true,
		"sPaginationType" : "bs_full",
		"aaData" : data,
		"bSort" : true
	});
}

function removeOk() {
	UsuarioProxy.findAll().done(findAllOk);
}
