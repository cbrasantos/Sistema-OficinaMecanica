<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
<title>Cadastro | Oficina </title>
</head>
<body class="pt-5">
    <h1 class="text-center">Cadastrar novo funcionário</h1>
    <form class="d-flex flex-column w-75 m-auto" method="POST" action="EmployeeController">
        <label class="mb-1">Nome Completo:</label>
        <input
            type="text" 
            name="name"
            id="name"
            placeholder="Nome Completo"
            class="mb-2" />
        
        <label class="mb-1">CPF:</label>
        <input
            type="text" 
            name="cpf"
            id="cpf"
            placeholder="CPF"
            class="mb-2" />
        
        <label class="mb-1">Telefone:</label>
        <input
            type="text"
            name="tel"
            id="tel"
            placeholder="Telefone"
            class="mb-2" />
        
        <label class="mb-1">Endereço:</label>
        <input 
            type="text"
            name="address"
            id="address"
            placeholder="Endereço"
            class="mb-2" />
        
        <label class="mb-1">E-mail:</label>
        <input 
            type="text"
            name="email"
            id="email"
            placeholder="E-mail"
            class="mb-2" />
        
        <label class="mb-1">Cargo:</label>
        <input 
            type="text"
            name="position"
            id="position"
            placeholder="Cargo"
            class="mb-2" />  
        
        <label class="mb-1">Salário:</label>
        <input 
            type="text"
            name="salary"
            id="salary"
            placeholder="Salário"
            class="mb-2" />
        
        <label class="mb-1">Carga Horária:</label>
        <input 
            type="text"
            name="workHours"
            id="workHours"
            placeholder="Carga Horária"
            class="mb-2" />  
        
        <label class="mb-1">Data de Contratação: </label>
        <input 
            type="text"
            name="contractDate"
            id="contractDate"
            placeholder="00/00/0000"
            class="mb-2" />  
        
        <input type="submit" value="Submit" class="btn btn-primary w-25 align-self-end" />
    </form>
</body>
</html>