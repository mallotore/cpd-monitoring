<tr id="server_configuration_container_${server.id}" style="font-weight: normal !important;"> 
    <th scope="row" style="width: 20%">
        <span>
            <label id="name_label_${server.id}" style="font-weight: normal;">${server.name}</label>
            <input id="name_text_${server.id}" value="${server.name}" type="text" class="form-control" style="display:none;" placeholder="Nombre">
        </span>
    </th>
    <td  style="width: 30%">
        <span>
            <label id="ip_label_${server.id}" style="width: 100%;font-weight: normal;">${server.ip}</label>
            <input id="ip_text_${server.id}" value="${server.ip}" type="text" class="form-control" style="display:none;" placeholder="IP">
        </span>
    </td> 
    <td  style="width: 15%">
        <span>
            <label id="port_label_${server.id}" style="width: 100%;font-weight: normal;">${server.port}</label>
            <input id="port_text_${server.id}" value="${server.port}" type="text" class="form-control" style="display:none;" placeholder="Puerto">
        </span>
    </td> 
    <td style="width: 20%">
        <span>
            <label id="probeInterval_label_${server.id}" style="width: 100%;font-weight: normal;">${server.probeInterval}</label>
            <input id="probeInterval_text_${server.id}" value="${server.probeInterval}" type="text" class="form-control" style="display:none;" placeholder="Servicio">
        </span>
    </td>
    <td style="width: 15%">
        <div id="actionButtons_${server.id}">
            <button id="showEditServerButton_${server.id}" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
            </button>
            <button id="deleteServerButton_${server.id}" type="button" class="btn btn-default">
                  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
        </div>
        <div id="editButtons_${server.id}" style="display:none;">
            <button id="updateServerButton_${server.id}" type="button" class="btn btn-default">
              <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
            </button>
            <button id="cancelServerEditionButton_${server.id}" type="button" class="btn btn-default">
              <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </button>
        </div>
    </td> 
</tr>