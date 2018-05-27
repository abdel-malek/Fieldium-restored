
var table = null;
var columns = [];
function create_table() {
    columns = [];
    table = $('#report').dataTable({
//         "bJQueryUI": true,
        "scrollX": true,
        "responsive": true,
//        "scrollCollapse": true,
//        "fixedColumns": {
//            leftColumns: 1
//        },
           "language": {
    	"sProcessing":   "جارٍ التحميل...",
	"sLengthMenu":   "أظهر _MENU_ مدخلات",
	"sZeroRecords":  "لم يعثر على أية سجلات",
	"sInfo":         "إظهار _START_ إلى _END_ من أصل _TOTAL_ مدخل",
	"sInfoEmpty":    "يعرض 0 إلى 0 من أصل 0 سجل",
	"sInfoFiltered": "(منتقاة من مجموع _MAX_ مُدخل)",
	"sInfoPostFix":  "",
	"sSearch":       "ابحث:",
	"sUrl":          "",
        "buttons": {
            "print":    "طباعة"
            },
	"oPaginate": {
		"sFirst":    "الأول",
		"sPrevious": "السابق",
		"sNext":     "التالي",
		"sLast":     "الأخير"
	}
        },
        "aLengthMenu": [[10, 25, 50, 75, -1], [10, 25, 50, 75, "All"]],
        "pageLength": 10,
        "dom": 'lBfrtip',
        buttons: [
//            {
//                message: '',
//                extend: 'pdfHtml5',
//                header: true,
//                customize: function (doc) {
//                    for (var i = 0; i < doc.content[1].table.body[0].length; i++) {
//                        doc.content[1].table.body[0][i].text = columns[i];
//                    }
//                    doc.content.forEach(function (content) {
//
//                        if (content.style == 'message') {
//                            var text = "";
//                            if ($("#from_date").val() != "")
//                                text += 'From : '
//                                        + $("#from_date").val() +
//                                        '\n';
//                            if ($("#to_date").val() != "")
//                                text += 'To : '
//                                        + $("#to_date").val() +
//                                        '\n';
//                            content.text = text;
//                        }
//                    })
//                }
//            },
//            {
//                extend: 'csv',
//                customize: function (csv) {
//                    var text = "";
//                    var res = csv.split(",");
//                    console.log(res);
//                    var result = [];
//                    for (var i = 0; i < columns.length; i++) {
//                        res[i] = '"' + columns[i] + '"';
////                        if (i == columns.length - 1)
////                            result[i] += '\n';
//                    }
//                    var first = true;
//                    for (var i = 0; i < res.length; i++) {
//                        if (!first) {
//                            if (i % (columns.length) == 0) {
//                                result.push('\n' + res[i]);
//                            } else {
//                                result.push(res[i]);
//                            }
//                        }
//                        first = false;
//                    }
//
//                    csv = result.toString();
//                    console.log(csv);
//                    if ($("#from_date").val() != "")
//                        text += 'From : '
//                                + $("#from_date").val() +
//                                '\n';
//                    if ($("#to_date").val() != "")
//                        text += 'To : '
//                                + $("#to_date").val() +
//                                '\n';
//                    return text + csv;
//                }
//            },
            {
                extend: 'excel',
                customize: function (doc) {

                    return "My header here....\n\n" + doc + "\n\nMy Footer here.....";
                }
            },
            {
                extend: 'print',
                customize: function (win) {

                    $(win.document.body).find('thead tr th').each(function (i) {
                        $(this).html(columns[i]);
                    });
                    if ($("#to_date").val() != "")
                        $(win.document.body)
                                .prepend(
                                        '<label  >To : </label>' + $("#to_date").val());
                    if ($("#from_date").val() != "")
                        $(win.document.body)
                                .prepend('<label  >From : </label>'
                                        + $("#from_date").val() +
                                        '<br>');
                    var w = append_print(win.document.body);
                    win.document.body = w;
                }
            }
        ],
//        initComplete: function () {
//            this.api().columns().every(function () {
//                var column = this;
//                var text = "";
//                if ($(column.header()).find("b").length != 0)
//                    text = $(column.header()).find("b").html();
//                else
//                    text = $(column.header()).html();
//                columns.push(text);
//                if (text != "") {
//                    $(column.header()).empty();
//                    $(column.header()).attr("text", text);
//                    $(column.header()).html("<b>" + text + "</b><br>");
//                    var select = $('<select class="form-control" style="height:15px;width:80%"><option value=""></option></select>')
//                            .appendTo($(column.header()))
//                            .on('change', function () {
//                                var val = $.fn.dataTable.util.escapeRegex(
//                                        $(this).val()
//                                        );
//
//                                column
//                                        .search(val ? '^' + val + '$' : '', true, false)
//                                        .draw();
//                            });
//
//                    column.data().unique().sort().each(function (d, j) {
//                        select.append('<option value="' + d + '">' + d + '</option>')
//                    });
//                }
//            });
//        }
    });
    table.destroy = function () {
        table.DataTable().destroy();
    }
    $('#report thead tr th:first select').remove();
}
function append_print(win) {
//
//    var tr = $('#report').find('tbody').find('tr');
//    var row = table.api().row(tr);
//
//    var tr_index = 0;
//    for (var i = 0; i < row.table().rows()[0].length; i++)
//    {
//        var r = $('#report').find('tbody').find('tr:nth-child(' + (i + 1) + ')');
//        var items = r.find('td:nth-child(1)').find("a").attr("items");
//        items = JSON.parse(items || null);
//        if (items.length != 0) {
//            var new_row = document.createElement('tr');
//            new_row.insertCell(0).innerHTML = '';
//            new_row.insertCell(1).innerHTML = '<td>Customer</td>';
//            new_row.insertCell(2).innerHTML = '<td>Product</td>';
//            new_row.insertCell(3).innerHTML = '<td>Quantity</td>';
//            new_row.insertCell(4).innerHTML = '<td>Sales Price</td>';
//            new_row.insertCell(5).innerHTML = '<td>Unit Cost</td>';
//            new_row.insertCell(6).innerHTML = '<td>Sales Contract</td>';
//            new_row.insertCell(7).innerHTML = '<td>Purchase Contract</td>';
//            $(win).find('table').find('tbody').find('tr')[tr_index].after(new_row);
//            tr_index++;
//            for (var j = 0; j < items.length; j++)
//            {
//                var new_row = document.createElement('tr');
//                new_row.insertCell(0).innerHTML = '';
//                new_row.insertCell(1).innerHTML = "<td>" + items[j].customer_company + "</td>";
//                new_row.insertCell(2).innerHTML = "<td>" + items[j].product_name + "</td>";
//                new_row.insertCell(3).innerHTML = "<td>" + items[j].quantity + " " + items[j].unit + "</td>";
//                new_row.insertCell(4).innerHTML = "<td>" + items[j].unit_sale_price + "</td>";
//                new_row.insertCell(5).innerHTML = "<td>" + items[j].unit_cost + "</td>";
//                new_row.insertCell(6).innerHTML = "<td>" + items[j].sales_contract_id + "</td>";
//                new_row.insertCell(7).innerHTML = "<td>" + items[j].contract_reference + "</td>";
//                if (j + 1 == items.length && j == 0)
//                {
//                    $(win).find('table').find('tbody').find('tr')[tr_index].setAttribute("style", "background-color: #e4e6e9;");
//                    new_row.setAttribute("style", "");
//                } else
//                if (j + 1 == items.length) {
//                    new_row.setAttribute("style", "border-bottom: 1px solid black;");
//                } else
//                if (j == 0) {
//                    $(win).find('table').find('tbody').find('tr')[tr_index].setAttribute("style", "background-color: #e4e6e9;");
//                    new_row.setAttribute("style", "");
//                }
//
//                $(win).find('table').find('tbody').find('tr')[tr_index].after(new_row);
//                tr_index++;
//            }
//        }
//
//        tr_index++;
//    }
    return win;
}
function render_items(ob) {
    var tr = $(ob).parent("td").parent('tr');
    var row = table.api().row(tr);

    if (row.child.isShown()) {
        // This row is already open - close it
        row.child.hide();
        $(ob).find("i").attr("class", "fa fa-plus-circle");
    } else {
        // Open this row
        row.child(format_shipment_items(row.node())).show();
        $(ob).find("i").attr("class", "fa fa-minus-circle");
    }
}
//create_table();
$("#from_date").datepicker(
        {
            dateFormat: 'yy-mm-dd',
            changeYear: true,
            changeMonth: true
        });
$("#to_date").datepicker(
        {
            dateFormat: 'yy-mm-dd',
            changeYear: true,
            changeMonth: true
        });
function format_shipment_items(d) {
    var items = JSON.parse($(d).find('td:nth-child(1)').find("a").attr("items"));
    var html = '<table class="table" style="margin: 0px;padding: 0px;">' +
            '<thead><tr class="header">' +
            '<th class="header">Customer</th>' +
            '<th class="header">Product</th>' +
            '<th class="header">Quantity</th>' +
            '<th class="header">Sales price</th>' +
            '<th class="header">Unit Cost</th>' +
            '<th class="header">Unit</th>' +
            '<th class="header">Sales Contract</th>' +
            '<th class="header">Purchase Contract</th>' +
            '</thead><tbody>';
    for (var i = 0; i < items.length; i++) {
        html += "<tr class='active'><td>" + items[i].customer_company + "</td>" +
                "<td>" + items[i].product_name + "</td>" +
                "<td>" + items[i].quantity + "</td>" +
                "<td>" + items[i].unit_sale_price + "</td>" +
                "<td>" + items[i].unit_cost + "</td>" +
                "<td>" + items[i].unit + "</td>" +
                "<td>" + items[i].sales_contract_id + "</td>" +
                "<td>" + items[i].contract_reference + "</td>" +
                "</tr>";
    }
    html += '</tbody></table>'
    return html;
}
