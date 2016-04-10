<tr id="addNewServerContainer" style="display:none;"> 
    <th scope="row" style="width: 15%">
        <span>
            <input id="name_addNewServer" type="text" class="form-control" placeholder="Nombre">
        </span>
    </th>
    <td style="width: 15%">
        <span>
            <input id="ip_addNewServer" type="text" class="form-control" placeholder="IP">
        </span>
    </td> 
    <td style="width: 10%">
        <span>
            <input id="port_addNewServer" type="text" class="form-control" placeholder="Puerto">
        </span>
    </td> 
    <td style="width: 20%">
        <span>
            <input id="probeInterval_addNewServer" type="text" class="form-control" placeholder="Intervalo">
        </span>
    </td>
    <td style="width: 25%">
         <table>
            <tr>
                <td>
                    <label>Espacio (%)</label>
                </td>
                <td>
                    <input id="diskSpaceAlert_addNewServer" type="text" class="form-control" placeholder="porcentaje" style="normal;margin-left:10px">
                </td>
            </tr>
            <tr>
                <td>
                    <label>Conectividad</label>
                </td>
                <td>
                    <input id="connectivityAlert_addNewServer" type="checkbox" style="margin-left:10px">
                </td>
            </tr>
        </table>
    </td>
    <td style="width: 15%">
        <button id="save_addNewServer" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
        </button>
        <button id="cancel_addNewServer" type="button" class="btn btn-default">
            <span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>
        </button>
    </td> 
</tr>