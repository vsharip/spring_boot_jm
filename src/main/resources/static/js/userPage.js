// ========================================================================================
// Пример стартового JavaScript для отключения отправки форм при наличии недопустимых полей
// (function () {
//   'use strict'
//   window.addEventListener('load', function () {
//     // Получите все формы, к которым мы хотим применить пользовательские стили проверки Bootstrap
//     const forms = document.getElementsByClassName('formCreateUser')
//     // Зацикливайтесь на них и предотвращайте подчинение
//     const validation = Array.prototype.filter.call(forms, function (form) {
//       form.addEventListener('submit', function (event) {
//         if (form.checkValidity() === false) {
//           event.preventDefault()
//           event.stopPropagation()
//         }
//         form.classList.add('was-validated')
//       }, false)
//     })
//   }, false)
// })()

// ==================================================================

async function userRest () {
  try {
    const response = await fetch('http://localhost:8080/user/user-auth')
    console.log("Авториация" + response)
    if (!response.ok) {
      throw Error('ERROR')
    }
    const data = await response.json()
    console.log(data)
    userListPageUser(data)
  } catch (error) {
    console.log(error)
  }
}

userRest()

function userListPageUser (user) {
  document.getElementById('h5Email').innerHTML = `
         <td>${user.email}</td>`
  document.getElementById('h5RoleSet').innerHTML = `
         ${user.roles.map(e => '<td>' + e.name + '</td>').join(' ')}`
  document.getElementById('tableUserInfo').innerHTML = `
        <tr class="table-active">
        <td>${user.id}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
         <td>
            ${user.roles.map(e => '<div class="div1">' + e.name + '</div>').join(' ')}
         </td>
   </tr>
`
}

async function tableAllUsers () {
  try {
    const response = await fetch('http://localhost:8080/admin/all-users')
    console.log(response)
    if (!response.ok) {
      throw Error('ERROR')
    }
    const data = await response.json()
    console.log(data)
    tableUserList(data)
    console.log(data)
  } catch (error) {
    console.log(error)
  }
}

tableAllUsers()

function tableUserList (user) {
  if (user.length > 0) {
    let temp = ''
    user.forEach((u) => {
      temp += '<tr id="userTableRow_' + u.id + '">'
      temp += '<td id="idUserTable">' + u.id + '</td>'
      temp += '<td id="firstNameUserTable">' + u.firstName + '</td>'
      temp += '<td id="lastNameUserTable">' + u.lastName + '</td>'
      temp += '<td id="ageUserTable">' + u.age + '</td>'
      temp += '<td id="emailUserTable">' + u.email + '</td>'
      temp += '<td id="rolesUserTable">' + u.roles.map(e => '<div class="div1">' + e.name + '</div>').join(' ') + '</td>'
      temp += '<td>' + '<button type="button" class="btn btn-primary btn-sm"' +
        'id="buttonEditUser" data-toggle="modal" data-target="#modalUpdate" value="' + u.id + '">Edit</button>' + '</td>'
      temp += '<td>' + '<button type="button" class="btn btn-danger btn-sm" ' +
        'id="buttonDeleteUser" data-toggle="modal" data-target="#modalDelete" value="' + u.id + '">Delete</button>' + '</td>'
    })
    document.getElementById('tableUserListAdminPage').innerHTML = temp
  }
}

async function roleList () {
  try {
    const response = await fetch('http://localhost:8080/admin/all-roles')
    console.log(response)
    if (!response.ok) {
      throw Error('ERROR')
    }
    const data = await response.json()
    console.log(data)
    tableRoleList(data)
  } catch (error) {
    console.log(error)
  }
}

roleList()

function tableRoleList (role) {
  document.getElementById('selectRoleUserCreateUser').innerHTML = `
     ${role.map(e => '<option  value=' + e.name + '>' + e.name + '</option>').join(' ')}
`
  document.getElementById('roleModalUpdate').innerHTML = `
  ${role.map(e => '<option  value=' + e.name + '>' + e.name + '</option>').join(' ')}
  `
}

const modalUserUpdate = document.querySelector('#modalFormUpdateUser')
const modalUserDelete = document.querySelector('#modalFormDeleteUser')
const tableUserAll = document.querySelector('#tableUserListAdminPage')

//           # Данные для нового юзера

const addUserForm = document.querySelector('.formCreateUser')

const firstNamePost = document.getElementById('firstNameCreateUser')
const lastNamePost = document.getElementById('lastNameCreateUser')
const agePost = document.getElementById('ageCreateUser')
const emailPost = document.getElementById('emailCreateUser')
const passwordPost = document.getElementById('passwordCreateUser')

addUserForm.addEventListener('submit', async (e) => {
  e.preventDefault()
  const response = await fetch('http://localhost:8080/admin/test', {
    method: 'POST',
    headers: {
      'Content-type': 'application/json'
    },
    body: JSON.stringify({
      firstName: firstNamePost.value,
      lastName: lastNamePost.value,
      age: agePost.value,
      email: emailPost.value,
      password: passwordPost.value,
      roles: $('#selectRoleUserCreateUser').val()
    }),
  })
  if (response.ok) {
    const json = await response.json()
    const jsonUserDto = JSON.stringify(json)
    $('#tableUserListAdminPage').append('<tr>' +
      '<td>' + json.id + '</td>' +
      '<td>' + json.firstName + '</td>' +
      '<td>' + json.lastName + '</td>' +
      '<td>' + json.age + '</td>' +
      '<td>' + json.email + '</td>' +
      '<td>' + json.roles.map(e => '<div class="div1">' + e.name + '</div>').join(' ') + '</td>' +
      '<td>' + '<button type="button" class="btn btn-primary btn-sm"' +
      'id="buttonEditUser" data-toggle="modal" data-target="#modalUpdate" value="' + json.id + '">Edit</button>' + '</td>' +
      '<td>' + '<button type="button" class="btn btn-danger btn-sm" ' +
      'id="buttonDeleteUser" data-toggle="modal" data-target="#modalDelete" value="' + json.id + '">Delete</button>' + '</td>' +
      '</tr>')

    console.log(jsonUserDto)
    $('.formCreateUser').trigger('reset')
  }
})

//#            Открытие модальных окон

tableUserAll.addEventListener('click', async (e) => {

  e.preventDefault()

  let editButtonIsPressed = e.target.id === 'buttonEditUser'
  let deleteButtonIsPressed = e.target.id === 'buttonDeleteUser'
  let val = e.target.value

  let response = await fetch('http://localhost:8080/admin/' + val)

  const user = await response.json()

  console.log(user)
  if (editButtonIsPressed) {
    $('#modalUpdate #idModalUpdate').val(user.id)
    $('#modalUpdate #firstNameModalUpdate').val(user.firstName)
    $('#modalUpdate #lastNameModalUpdate').val(user.lastName)
    $('#modalUpdate #ageModalUpdate').val(user.age)
    $('#modalUpdate #emailModalUpdate').val(user.email)
    $('#modalUpdate #roleModalUpdate').val(user.roles)
    $('#modalUpdate').modal('show')

  } else if (deleteButtonIsPressed) {

    $('#modalDelete #idModalDelete').val(user.id)
    $('#modalDelete #firstNameModalDelete').val(user.firstName)
    $('#modalDelete #lastNameModalDelete').val(user.lastName)
    $('#modalDelete #ageModalDelete').val(user.age)
    $('#modalDelete #emailModalDelete').val(user.email)
    let roles = document.getElementById('roleModalDelete').innerHTML = `
    ${user.roles.map(e => '<option  value=' + e.name + '>' + e.name + '</option>').join(' ')}`
    $('#modalDelete').modal('show')
  }
})

//           # Данные для модального окна обновления

const idUpdateModal = document.getElementById('idModalUpdate')
const firstNameUpdateModal = document.getElementById('firstNameModalUpdate')
const lastNameUpdateModal = document.getElementById('lastNameModalUpdate')
const ageUpdateModal = document.getElementById('ageModalUpdate')
const emailUpdateModal = document.getElementById('emailModalUpdate')
const passwordUpdateModal = document.getElementById('passwordModalUpdate')

modalUserUpdate.addEventListener('submit', async (e) => {

  e.preventDefault()

  const response = await fetch('http://localhost:8080/admin/test', {
    method: 'PUT',
    headers: {
      'Content-type': 'application/json'
    },
    body: JSON.stringify({
      id: idUpdateModal.value,
      firstName: firstNameUpdateModal.value,
      lastName: lastNameUpdateModal.value,
      age: ageUpdateModal.value,
      email: emailUpdateModal.value,
      password: passwordUpdateModal.value,
      roles: $('#roleModalUpdate').val()
    })
  })
  const json = await response.json()
  const jsonUserDto = JSON.stringify(json)
  console.log('Успешно' + jsonUserDto)
  if (response.ok === true) {
    const tr = $('#userTableRow_' + json.id)
    let update = ''
    update += '<td id="idUserTable">' + json.id + '</td>'
    update += '<td id="firstNameUserTable">' + json.firstName + '</td>'
    update += '<td id="lastNameUserTable">' + json.lastName + '</td>'
    update += '<td id="ageUserTable">' + json.age + '</td>'
    update += '<td id="emailUserTable">' + json.email + '</td>'
    update += '<td id="rolesUserTable">' + json.roles.map(e => '<div class="div1">' + e.name + '</div>').join(' ') + '</td>'
    update += '<td>' + '<button type="button" class="btn btn-primary btn-sm"' +
      'id="buttonEditUser" data-toggle="modal" data-target="#modalUpdate" value="' + json.id + '">Edit</button>' + '</td>'
    update += '<td>' + '<button type="button" class="btn btn-danger btn-sm" ' +
      'id="buttonDeleteUser" data-toggle="modal" data-target="#modalDelete" value="' + json.id + '">Delete</button>' + '</td>'
    tr.after(update)
    tr.remove();
    $('#modalUpdate').modal('hide')
  }
})

//           # Данные для модального окна удаления

const idDeleteModal = document.getElementById('idModalDelete')

modalUserDelete.addEventListener('submit', async (e) => {
  e.preventDefault()
  await fetch('http://localhost:8080/admin/' + idDeleteModal.value, {
    method: 'DELETE',
  }).then(response => {
      if (response.ok === true) {
        $('#userTableRow_' + idDeleteModal.value).remove()
        $('#modalDelete').modal('hide')
      }
    }
  )
})



















