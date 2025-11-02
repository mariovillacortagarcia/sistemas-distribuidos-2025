# Servlet Shopping - API REST CRUD

Sistema de gestión de usuarios y carritos de compra implementado con Java Servlets.

## Descripción

Esta aplicación proporciona una API REST para gestionar usuarios y sus carritos de compra. Incluye operaciones CRUD completas siguiendo la semántica HTTP estándar. Todas las respuestas son en formato JSON.

## Componentes

- **UserServlet**: Gestión de usuarios (CRUD)
- **ShoppingCartServlet**: Gestión de carritos de compra y productos

## Endpoints

### UserServlet

- `GET /Users` - Lista todos los usuarios
- `GET /Users/{userId}` - Obtiene un usuario específico (path parameter)
- `GET /Users?id={userId}` - Obtiene un usuario específico (query parameter, retrocompatible)
- `POST /Users` - Crea un nuevo usuario
- `PUT /Users` - Actualiza un usuario existente
- `DELETE /Users/{userId}` - Elimina un usuario (path parameter)
- `DELETE /Users?id={userId}` - Elimina un usuario (query parameter, retrocompatible)

### ShoppingCartServlet

- `GET /ShoppingCarts` - Lista todos los carritos
- `GET /ShoppingCarts/{userId}` - Obtiene el carrito de un usuario (path parameter)
- `GET /ShoppingCarts?userId={userId}` - Obtiene el carrito de un usuario (query parameter, retrocompatible)
- `POST /ShoppingCarts/{userId}` - Agrega un producto al carrito (crea el carrito si no existe)
- `POST /ShoppingCarts?userId={userId}` - Agrega un producto al carrito (query parameter, retrocompatible)
- `PUT /ShoppingCarts/{userId}` - Elimina un producto del carrito (crea el carrito si no existe)
- `PUT /ShoppingCarts?userId={userId}` - Elimina un producto del carrito (query parameter, retrocompatible)
- `DELETE /ShoppingCarts/{userId}` - Elimina un carrito completo (path parameter)
- `DELETE /ShoppingCarts?userId={userId}` - Elimina un carrito completo (query parameter, retrocompatible)

**Nota:** Los carritos se crean automáticamente cuando se añade o elimina un producto si no existen previamente.

## Ejemplos de Uso con cURL

### Configuración Base

Asumiendo que la aplicación está desplegada en `http://localhost:8080/servlet-shopping`:

```bash
BASE_URL="http://localhost:8080/servlet-shopping"
```

---

## Secuencia Operativa Completa

### 1. Crear Usuarios

```bash
# Crear usuario 1
curl -X POST "${BASE_URL}/Users" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=Juan Pérez"

# Crear usuario 2
curl -X POST "${BASE_URL}/Users" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=María García"

# Crear usuario 3
curl -X POST "${BASE_URL}/Users" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=Carlos López"
```

**Respuesta esperada:** JSON con el usuario creado (incluyendo su ID generado automáticamente)

**Ejemplo de respuesta:**

```json
{ "id": "aB3cD9eF2gH5iJ8kL", "name": "Juan Pérez" }
```

**Guardar los IDs para uso posterior:**

```bash
USER_ID_1="aB3cD9eF2gH5iJ8kL"  # Reemplazar con ID real
USER_ID_2="xY7zW4vU6tS1rQ9pO"  # Reemplazar con ID real
```

### 2. Listar Todos los Usuarios

```bash
curl -X GET "${BASE_URL}/Users"
```

**Respuesta:** JSON con array de todos los usuarios

**Ejemplo de respuesta:**

```json
[
  { "id": "aB3cD9eF2gH5iJ8kL", "name": "Juan Pérez" },
  { "id": "xY7zW4vU6tS1rQ9pO", "name": "María García" }
]
```

### 3. Obtener un Usuario Específico

```bash
# Usando path parameter (recomendado)
curl -X GET "${BASE_URL}/Users/${USER_ID_1}"

# O usando query parameter (retrocompatible)
curl -X GET "${BASE_URL}/Users?id=${USER_ID_1}"
```

**Respuesta:** JSON con los detalles del usuario

**Ejemplo de respuesta:**

```json
{ "id": "aB3cD9eF2gH5iJ8kL", "name": "Juan Pérez" }
```

### 4. Actualizar un Usuario

```bash
curl -X PUT "${BASE_URL}/Users" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "id=${USER_ID_1}" \
  -d "name=Juan Pérez Actualizado"
```

**Respuesta:** JSON confirmando la actualización

**Ejemplo de respuesta:**

```json
{ "id": "aB3cD9eF2gH5iJ8kL", "name": "Juan Pérez Actualizado" }
```

### 5. Agregar Productos al Carrito

**Nota:** Los carritos se crean automáticamente si no existen al agregar el primer producto.

```bash
# Agregar primer producto al carrito del usuario 1 (usando path parameter)
curl -X POST "${BASE_URL}/ShoppingCarts/${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Laptop" \
  -d "productBrand=Dell"

# Agregar segundo producto (usando query parameter)
curl -X POST "${BASE_URL}/ShoppingCarts?userId=${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Mouse" \
  -d "productBrand=Logitech"

# Agregar tercer producto
curl -X POST "${BASE_URL}/ShoppingCarts/${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Teclado" \
  -d "productBrand=Corsair"

# Agregar producto al carrito del usuario 2 (se crea automáticamente)
curl -X POST "${BASE_URL}/ShoppingCarts/${USER_ID_2}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Monitor" \
  -d "productBrand=Samsung"
```

**Respuesta:** JSON con el carrito actualizado

**Ejemplo de respuesta:**

```json
{
  "userId": "aB3cD9eF2gH5iJ8kL",
  "products": [
    { "name": "Laptop", "brand": "Dell" },
    { "name": "Mouse", "brand": "Logitech" }
  ]
}
```

### 6. Ver el Contenido del Carrito

```bash
# Ver carrito del usuario 1 (usando path parameter)
curl -X GET "${BASE_URL}/ShoppingCarts/${USER_ID_1}"

# Ver carrito del usuario 2 (usando query parameter)
curl -X GET "${BASE_URL}/ShoppingCarts?userId=${USER_ID_2}"
```

**Respuesta:** JSON con el carrito y sus productos

**Ejemplo de respuesta:**

```json
{
  "userId": "aB3cD9eF2gH5iJ8kL",
  "products": [
    { "name": "Laptop", "brand": "Dell" },
    { "name": "Mouse", "brand": "Logitech" }
  ]
}
```

### 7. Listar Todos los Carritos

```bash
curl -X GET "${BASE_URL}/ShoppingCarts"
```

**Respuesta:** JSON con array de todos los carritos

**Ejemplo de respuesta:**

```json
[
  {
    "userId": "aB3cD9eF2gH5iJ8kL",
    "products": [{ "name": "Laptop", "brand": "Dell" }]
  },
  { "userId": "xY7zW4vU6tS1rQ9pO", "products": [] }
]
```

### 8. Eliminar Producto del Carrito

```bash
# Eliminar Mouse del carrito del usuario 1 (usando path parameter)
curl -X PUT "${BASE_URL}/ShoppingCarts/${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Mouse" \
  -d "productBrand=Logitech"

# O usando query parameter
curl -X PUT "${BASE_URL}/ShoppingCarts?userId=${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Mouse" \
  -d "productBrand=Logitech"
```

**Respuesta:** JSON con el carrito actualizado

**Ejemplo de respuesta:**

```json
{
  "userId": "aB3cD9eF2gH5iJ8kL",
  "products": [{ "name": "Laptop", "brand": "Dell" }]
}
```

### 9. Eliminar Carrito de Compra

```bash
# Eliminar carrito del usuario 2 (usando path parameter)
curl -X DELETE "${BASE_URL}/ShoppingCarts/${USER_ID_2}"

# O usando query parameter
curl -X DELETE "${BASE_URL}/ShoppingCarts?userId=${USER_ID_2}"
```

**Respuesta:** JSON confirmando la eliminación

**Ejemplo de respuesta:**

```json
{
  "message": "Shopping cart deleted successfully",
  "userId": "xY7zW4vU6tS1rQ9pO"
}
```

### 10. Eliminar Usuario

```bash
# Eliminar usuario 3 (usando path parameter)
curl -X DELETE "${BASE_URL}/Users/${USER_ID_3}"

# O usando query parameter
curl -X DELETE "${BASE_URL}/Users?id=${USER_ID_3}"
```

**Respuesta:** JSON confirmando la eliminación

**Ejemplo de respuesta:**

```json
{ "message": "User deleted successfully", "id": "mN5oP7qR3sT9uV1wX" }
```

---

## Ejemplos Adicionales

### Ver Headers de Respuesta

```bash
curl -X POST "${BASE_URL}/Users" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=Test User" \
  -i
```

### Formatear Respuesta JSON (requiere jq)

```bash
curl -X GET "${BASE_URL}/Users" | jq
```

### Manejo de Errores

#### Intentar crear usuario sin nombre (debe retornar 400 Bad Request)

```bash
curl -X POST "${BASE_URL}/Users" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=" \
  -w "\nHTTP Status: %{http_code}\n"
```

**Respuesta esperada:**

```json
{ "error": "Bad Request", "message": "Name parameter is required." }
```

#### Intentar obtener usuario inexistente (debe retornar 404 Not Found)

```bash
curl -X GET "${BASE_URL}/Users/ID_INEXISTENTE" \
  -w "\nHTTP Status: %{http_code}\n"
```

**Respuesta esperada:**

```json
{
  "error": "User not found",
  "message": "User with ID ID_INEXISTENTE does not exist."
}
```

#### Intentar actualizar usuario sin ID (debe retornar 400 Bad Request)

```bash
curl -X PUT "${BASE_URL}/Users" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=Nuevo Nombre" \
  -w "\nHTTP Status: %{http_code}\n"
```

**Respuesta esperada:**

```json
{ "error": "Bad Request", "message": "ID parameter is required." }
```

#### Intentar agregar producto sin datos completos (debe retornar 400 Bad Request)

```bash
curl -X POST "${BASE_URL}/ShoppingCarts/${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Laptop" \
  -w "\nHTTP Status: %{http_code}\n"
```

**Respuesta esperada:**

```json
{
  "error": "Bad Request",
  "message": "Product name and brand parameters are required."
}
```

---

## Script de Prueba Completo

```bash
#!/bin/bash

BASE_URL="http://localhost:8080/servlet-shopping"

echo "=== Creando usuarios ==="
USER1_RESPONSE=$(curl -s -X POST "${BASE_URL}/Users" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=Juan Pérez")
USER_ID_1=$(echo "$USER1_RESPONSE" | grep -o '"id":"[^"]*"' | cut -d'"' -f4)
echo "Usuario 1 ID: $USER_ID_1"
echo "Respuesta: $USER1_RESPONSE"

USER2_RESPONSE=$(curl -s -X POST "${BASE_URL}/Users" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=María García")
USER_ID_2=$(echo "$USER2_RESPONSE" | grep -o '"id":"[^"]*"' | cut -d'"' -f4)
echo "Usuario 2 ID: $USER_ID_2"
echo "Respuesta: $USER2_RESPONSE"

echo -e "\n=== Listando usuarios ==="
curl -s "${BASE_URL}/Users" | python -m json.tool 2>/dev/null || curl -s "${BASE_URL}/Users"

echo -e "\n=== Agregando productos al carrito (se crea automáticamente) ==="
curl -s -X POST "${BASE_URL}/ShoppingCarts/${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Laptop" \
  -d "productBrand=Dell" | python -m json.tool 2>/dev/null || curl -s -X POST "${BASE_URL}/ShoppingCarts/${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Laptop" \
  -d "productBrand=Dell"

curl -s -X POST "${BASE_URL}/ShoppingCarts/${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Mouse" \
  -d "productBrand=Logitech" | python -m json.tool 2>/dev/null || curl -s -X POST "${BASE_URL}/ShoppingCarts/${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Mouse" \
  -d "productBrand=Logitech"

echo -e "\n=== Verificando carrito ==="
curl -s "${BASE_URL}/ShoppingCarts/${USER_ID_1}" | python -m json.tool 2>/dev/null || curl -s "${BASE_URL}/ShoppingCarts/${USER_ID_1}"

echo -e "\n=== Eliminando producto del carrito ==="
curl -s -X PUT "${BASE_URL}/ShoppingCarts/${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Mouse" \
  -d "productBrand=Logitech" | python -m json.tool 2>/dev/null || curl -s -X PUT "${BASE_URL}/ShoppingCarts/${USER_ID_1}" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "productName=Mouse" \
  -d "productBrand=Logitech"

echo -e "\n=== Eliminando carrito ==="
curl -s -X DELETE "${BASE_URL}/ShoppingCarts/${USER_ID_2}" | python -m json.tool 2>/dev/null || curl -s -X DELETE "${BASE_URL}/ShoppingCarts/${USER_ID_2}"

echo -e "\n=== Eliminando usuario ==="
curl -s -X DELETE "${BASE_URL}/Users/${USER_ID_2}" | python -m json.tool 2>/dev/null || curl -s -X DELETE "${BASE_URL}/Users/${USER_ID_2}"

echo -e "\n=== Prueba completada ==="
```

---

## Códigos de Estado HTTP

- `200 OK` - Operación exitosa
- `201 Created` - Recurso creado exitosamente
- `400 Bad Request` - Parámetros faltantes o inválidos
- `404 Not Found` - Recurso no encontrado

## Notas

- **IDs de usuario**: Se generan automáticamente con 18 caracteres alfanuméricos usando `RandomIdGenerator`
- **Identificación de productos**: Los productos se identifican por nombre y marca (ambos deben coincidir exactamente)
- **Creación automática de carritos**: Los carritos se crean automáticamente cuando se añade o elimina un producto si no existen previamente
- **Formato de respuestas**: Todas las respuestas son en formato JSON con `Content-Type: application/json`
- **Path parameters**: Se recomienda el uso de path parameters (ej: `/Users/{id}`) en lugar de query parameters para mejor semántica REST
- **Retrocompatibilidad**: Los query parameters siguen funcionando para mantener compatibilidad con versiones anteriores

## Requisitos

- Java Servlet API
- Servidor de aplicaciones compatible con Java Servlets (Tomcat, GlassFish, etc.)
- cURL o herramienta HTTP similar para pruebas
