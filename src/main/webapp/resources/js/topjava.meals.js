const mealAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data, type, row) {
                        return data;
                    }
                },
                {
                    "data": "description",
                    "render": function (data, type, row) {
                        return data;
                    }
                },
                {
                    "data": "calories",
                    "render": function (data, type, row) {
                        return data;
                    }
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "rowCallback": function (row, data, dataIndex) {
                if (!data.excess) {
                    $(row).css("color", "green");
                }
                else $(row).css("color", "red");
            }
        }),
        updateTable: function () {
            updateFilteredTable();
        }
    });
});