$(document).ready(startFunction);
function startFunction() {
    const addBtn = document.getElementById("add-btn");
    addBtn.addEventListener("click", () => {
        console.log("click");
        adduser().then(() => {});
    });
    loadRows();
}

function loadRows() {
    setTimeout(() => {
        $.ajax({
            url: "/api/admin"
        }).then(function (data) {
            const parent = document.getElementById("all-users");
            parent.innerHTML = "";
            data.forEach((user) => {
                const newItem = createListItem(user);
                parent.appendChild(newItem);
                setTimeout(() => {
                    addEventListeners(user.id);
                })
            });
        });
    })

}

function addEventListeners(userId) {
    const modalEdBtn = document.getElementById(`edit-modal-btn${userId}`);
    const modalDelBtn = document.getElementById(`delete-modal-btn${userId}`);

    modalEdBtn.addEventListener("click", () => {
        const closeBtn = document.getElementById(`editCloseBtn${userId}`);
        closeBtn.click();
        upduser(userId).then(() => {});
    });

    modalDelBtn.addEventListener("click", () => {
        const closeBtn = document.getElementById(`deleteCloseBtn${userId}`);
        closeBtn.click();
        deluser(userId);
    });
}

function deluser(id) {
    $.ajax({
        url: `/api/admin/${id}`,
        type: "DELETE",
        success: loadRows
    });
}

async function adduser() {
    const requestBody = await formToJSON();
    $.ajax({
        url: `/api/admin`,
        contentType: 'application/json',
        type: "POST",
        dataType: "json",
        data: requestBody,
        success: () => {
            loadRows();
            $('form[name=addForm]').trigger('reset');
        }
    })
}

async function upduser(id) {
    const requestBody = await formToJSON(id);
    $.ajax({
        url: `/api/admin/${id}`,
        contentType: 'application/json',
        type: "PUT",
        dataType: "json",
        data: requestBody,
        success: () => {
            loadRows();
        }
    })
}

function formToJSON(id) {
    return new Promise(function (resolve) {
        let suffix = id === undefined
            ? "add"
            : `ed${id}`;
        $.ajax({
            url: "/api/role",
            data: `jsonRoles=${getRole(suffix)}`,

        }).then(function (data) {
            const result = JSON.stringify({
                "username": $(`#username-${suffix}`).val(),
                "password": $(`#password-${suffix}`).val(),
                "firstname": $(`#firstname-${suffix}`).val(),
                "lastname": $(`#lastname-${suffix}`).val(),
                "age": $(`#age-${suffix}`).val(),
                "email": $(`#email-${suffix}`).val(),
                "roles": data
            });
            resolve(result);
        });
    })
}

function getRole(suffix) {
    return $(`#role-${suffix}`).val();
}

function createListItem(user) {
    const item = document.createElement("tr");
    const roles = user.roles.reduce((accum, role) => {
        return accum === "" ?
            accum + role.name :
            accum + ", " + role.name;
    }, "");
    const content = `

            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.firstname}</td>
            <td>${user.lastname}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${roles}</td>
            <td>
                <button type="button" class="btn btn-info btn-sm"
                        data-toggle="modal"
                        data-target="#userEdit${user.id}">Edit
                </button>
                <div class="modal" id="userEdit${user.id}">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title text-dark">Edit user</h3>
                            </div>
                            <div class="modal-body">
                                <form id="userInfo${user.id}">
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">ID</p>
                                    <input class="form-control" type="text" 
                                           id="id-ed" placeholder="${user.id}" readonly>
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Username</p>
                                    <input type="text" class="form-control" id="username-ed${user.id}"
                                           placeholder="${user.username}">
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Password</p>
                                    <input type="text"
                                           class="form-control" id="password-ed${user.id}">
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">First name</p>
                                    <input type="text" class="form-control" id="firstname-ed${user.id}"
                                           placeholder="${user.firstname}">
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Last name</p>
                                    <input type="text" class="form-control" id="lastname-ed${user.id}"
                                           placeholder="${user.lastname}">
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Age</p>
                                    <input type="text" class="form-control" id="age-ed${user.id}"
                                           placeholder="${user.age}">
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Email</p>
                                    <input type="email" class="form-control" id="email-ed${user.id}"
                                           placeholder="${user.email}">
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Role</p>
                                    <div class="form-group">
                                        <select multiple class="form-control"
                                                name="role"
                                                id="role-ed${user.id}">
                                            <option value="user">User</option>
                                            <option value="admin">Admin</option>
                                        </select>
                                    </div>
                                    <div class="text-right">
                                         <button type="button" class="btn btn-secondary"
                                                 data-dismiss="modal" id="editCloseBtn${user.id}">Close
                                         </button>
                                         <input id="edit-modal-btn${user.id}" class="btn btn-primary" value="Edit">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </td> 
            <td>
                <button type="button" class="btn btn-danger btn-sm"
                        data-toggle="modal"
                        data-target="#userDelete${user.id}">Delete
                </button>
                <div class="modal" id="userDelete${user.id}">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h2 class="modal-title text-dark">Delete user</h2>
                            </div>
                            <div class="modal-body">
                                <form>
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">ID</p>
                                    <input class="form-control" type="text"
                                           id="idDel"
                                           placeholder="${user.id}" readonly>
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Username</p>
                                    <input type="text" class="form-control" id="usernameDel"
                                           placeholder="${user.username}" readonly>
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Password</p>
                                    <input type="text" class="form-control" id="passwordDel" readonly>
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">First name</p>
                                    <input type="text" class="form-control" id="firstnameDel"
                                           placeholder="${user.firstname}" readonly>
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Last name</p>
                                    <input type="text" class="form-control" id="lastnameDel"
                                           placeholder="${user.lastname}" readonly>
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Age</p>
                                    <input type="text" class="form-control" id="ageDel"
                                           placeholder="${user.age}" readonly>
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Email</p>
                                    <input type="email" class="form-control" id="emailDel"
                                           placeholder="${user.email}" readonly>
                                    <p class="font-weight-bold text-center text-dark mb-0 mt-2">Role</p>
                                    <div class="form-group">
                                        <select multiple class="form-control"
                                                id="roleDel">
                                            <option>${roles}</option>
                                        </select>
                                    </div>
                                    <div class="text-right">
                                        <button type="button" class="btn btn-secondary"
                                                data-dismiss="modal" id="deleteCloseBtn${user.id}">Close
                                        </button>
                                        <input class="btn btn-danger" id="delete-modal-btn${user.id}" value="Delete">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </td>  
        `;
    item.innerHTML = content;
    return item;
}