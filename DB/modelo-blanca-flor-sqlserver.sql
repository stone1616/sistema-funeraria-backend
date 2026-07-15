-- ============================================================
-- BLANCA FLOR - Script de creacion de base de datos
-- Motor: SQL Server (T-SQL)
-- Generado a partir del modelo v3 (con auditoria completa)
-- Orden de ejecucion: 1) CREATE TABLE  2) FOREIGN KEYS
-- (las FK se agregan al final para no depender del orden
--  de creacion de las tablas)
-- ============================================================

-- ================= SEGURIDAD Y ACCESO =================

CREATE TABLE [dbo].[Rol] (
    [idRol] INT IDENTITY(1,1) PRIMARY KEY,
    [nombreRol] VARCHAR(50),
    [descripcion] VARCHAR(150),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[Modulo] (
    [idModulo] INT IDENTITY(1,1) PRIMARY KEY,
    [nombreModulo] VARCHAR(50),
    [ruta] VARCHAR(100),
    [icono] VARCHAR(50),
    [orden] INT,
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[RolModulo] (
    [idRolModulo] INT IDENTITY(1,1) PRIMARY KEY,
    [idRol] INT NOT NULL,
    [idModulo] INT NOT NULL,
    [puedeVer] BIT,
    [puedeCrear] BIT,
    [puedeEditar] BIT,
    [puedeEliminar] BIT,
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

-- ================= PERSONAS =================

-- Estructura alineada al entity Cliente.java ya existente en el backend

CREATE TABLE [dbo].[Cliente] (
    [id_cliente] INT IDENTITY(1,1) PRIMARY KEY,
    [nombres] VARCHAR(100),
    [apellidos] VARCHAR(100),
    [dni] VARCHAR(10),
    [telefono] VARCHAR(15),
    [estado] BIT NOT NULL,
    [fechaCreacion] DATETIME NOT NULL,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT NOT NULL,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[Personal] (
    [idPersonal] INT IDENTITY(1,1) PRIMARY KEY,
    [nombres] VARCHAR(100),
    [apellidos] VARCHAR(100),
    [dni] VARCHAR(10),
    [telefono] VARCHAR(15),
    [direccion] VARCHAR(150),
    [cargo] VARCHAR(50),
    [estado] VARCHAR(30),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

-- Estructura alineada al entity Usuario.java ya existente en el backend.
-- Usuario NO duplica nombres/apellidos/dni: esos datos viven en Personal
-- y se referencian via idPersonal (evita desincronizacion si un empleado
-- cambia de nombre).

CREATE TABLE [dbo].[Usuario] (
    [id_usuario] INT IDENTITY(1,1) PRIMARY KEY,
    [idPersonal] INT NOT NULL UNIQUE,
    [email] VARCHAR(100) NOT NULL UNIQUE,
    [password] VARCHAR(255) NOT NULL,
    [rol] VARCHAR(20) NOT NULL,
    [estado] BIT NOT NULL,
    [fechaCreacion] DATETIME NOT NULL,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT NOT NULL,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

-- ================= EL DIFUNTO =================
-- Estructura alineada al entity Difunto.java ya existente en el backend
-- (unico modelo para el difunto; no se duplica con "Fallecido")

CREATE TABLE [dbo].[difunto] (
    [id_difunto] INT IDENTITY(1,1) PRIMARY KEY,
    [nombres] VARCHAR(100) NOT NULL,
    [apellidos] VARCHAR(100) NOT NULL,
    [dni] VARCHAR(10),
    [fechaNacimiento] DATE,
    [fechaDefuncion] DATE NOT NULL,
    [lugarDefuncion] VARCHAR(150),
    [causaDefuncion] VARCHAR(150),
    [idCliente] INT NOT NULL,
    [estado] BIT NOT NULL,
    [fechaCreacion] DATETIME NOT NULL,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT NOT NULL,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[DocumentoFallecido] (
    [idDocumento] INT IDENTITY(1,1) PRIMARY KEY,
    [idFallecido] INT NOT NULL,
    [tipoDocumento] VARCHAR(50),
    [urlArchivo] VARCHAR(255),
    [fechaCarga] DATETIME,
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

-- ================= RECURSOS =================

CREATE TABLE [dbo].[Vehiculo] (
    [idVehiculo] INT IDENTITY(1,1) PRIMARY KEY,
    [placa] VARCHAR(10),
    [marca] VARCHAR(50),
    [modelo] VARCHAR(50),
    [capacidad] INT,
    [estado] VARCHAR(30),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[Sala] (
    [idSala] INT IDENTITY(1,1) PRIMARY KEY,
    [nombreSala] VARCHAR(100),
    [capacidad] INT,
    [ubicacion] VARCHAR(100),
    [estado] VARCHAR(30),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[Producto] (
    [idProducto] INT IDENTITY(1,1) PRIMARY KEY,
    [nombre] VARCHAR(100),
    [categoria] VARCHAR(30),
    [material] VARCHAR(50),
    [color] VARCHAR(30),
    [precio] DECIMAL(10,2),
    [stock] INT,
    [estado] VARCHAR(30),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

-- ================= EL NUCLEO: SERVICIO FUNERARIO =================
-- (se crea antes de MovimientoInventario porque este ultimo lo referencia)

CREATE TABLE [dbo].[ServicioFunerario] (
    [idServicio] INT IDENTITY(1,1) PRIMARY KEY,
    [idCliente] INT NOT NULL,
    [idFallecido] INT NOT NULL UNIQUE,
    [parentescoCliente] VARCHAR(50),
    [tipoServicio] VARCHAR(100),
    [fechaServicio] DATETIME,
    [estado] VARCHAR(30),
    [descripcion] VARCHAR(255),
    [costoTotal] DECIMAL(10,2),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[MovimientoInventario] (
    [idMovimiento] INT IDENTITY(1,1) PRIMARY KEY,
    [idProducto] INT NOT NULL,
    [tipoMovimiento] VARCHAR(20),
    [cantidad] INT,
    [fecha] DATETIME,
    [idServicio] INT,
    [idPersonal] INT,
    [proveedor] VARCHAR(100),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[ServicioPersonal] (
    [idServicioPersonal] INT IDENTITY(1,1) PRIMARY KEY,
    [idServicio] INT NOT NULL,
    [idPersonal] INT NOT NULL,
    [rolEnServicio] VARCHAR(50),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[ReservaSala] (
    [idReserva] INT IDENTITY(1,1) PRIMARY KEY,
    [idServicio] INT NOT NULL,
    [idSala] INT NOT NULL,
    [fechaInicio] DATETIME,
    [fechaFin] DATETIME,
    [estado] VARCHAR(30),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[AsignacionVehiculo] (
    [idAsignacion] INT IDENTITY(1,1) PRIMARY KEY,
    [idServicio] INT NOT NULL,
    [idVehiculo] INT NOT NULL,
    [idPersonal] INT,
    [fechaHora] DATETIME,
    [estado] VARCHAR(30),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[DetalleServicioProducto] (
    [idDetalle] INT IDENTITY(1,1) PRIMARY KEY,
    [idServicio] INT NOT NULL,
    [idProducto] INT NOT NULL,
    [cantidad] INT,
    [precioUnitario] DECIMAL(10,2),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[DestinoFinal] (
    [idDestino] INT IDENTITY(1,1) PRIMARY KEY,
    [idServicio] INT NOT NULL UNIQUE,
    [tipo] VARCHAR(20),
    [cementerioCrematorio] VARCHAR(150),
    [panteonLoteNicho] VARCHAR(100),
    [numeroUrna] VARCHAR(50),
    [fechaDestino] DATETIME,
    [estado] VARCHAR(30),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

-- ================= DINERO =================

CREATE TABLE [dbo].[Cotizacion] (
    [idCotizacion] INT IDENTITY(1,1) PRIMARY KEY,
    [idServicio] INT NOT NULL UNIQUE,
    [montoEstimado] DECIMAL(10,2),
    [fechaCotizacion] DATETIME,
    [estado] VARCHAR(30),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[Comprobante] (
    [idComprobante] INT IDENTITY(1,1) PRIMARY KEY,
    [idServicio] INT NOT NULL UNIQUE,
    [tipoComprobante] VARCHAR(30),
    [numeroComprobante] VARCHAR(30),
    [ruc] VARCHAR(11),
    [razonSocial] VARCHAR(150),
    [fechaEmision] DATETIME,
    [subtotal] DECIMAL(10,2),
    [igv] DECIMAL(10,2),
    [total] DECIMAL(10,2),
    [estado] VARCHAR(30),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);

CREATE TABLE [dbo].[Pago] (
    [idPago] INT IDENTITY(1,1) PRIMARY KEY,
    [idComprobante] INT NOT NULL,
    [fechaPago] DATETIME,
    [monto] DECIMAL(10,2),
    [metodoPago] VARCHAR(30),
    [estadoPago] VARCHAR(30),
    [numeroRecibo] VARCHAR(30),
    [fechaCreacion] DATETIME,
    [fechaModificacion] DATETIME,
    [fechaEliminacion] DATETIME,
    [idUsuarioCreacion] INT,
    [idUsuarioModificacion] INT,
    [idUsuarioEliminacion] INT
);
GO

-- ============================================================
-- FOREIGN KEYS DE NEGOCIO
-- ============================================================

ALTER TABLE [dbo].[RolModulo] ADD CONSTRAINT FK_RolModulo_Rol FOREIGN KEY ([idRol]) REFERENCES [dbo].[Rol]([idRol]);
ALTER TABLE [dbo].[RolModulo] ADD CONSTRAINT FK_RolModulo_Modulo FOREIGN KEY ([idModulo]) REFERENCES [dbo].[Modulo]([idModulo]);

ALTER TABLE [dbo].[DocumentoFallecido] ADD CONSTRAINT FK_DocumentoFallecido_Difunto FOREIGN KEY ([idFallecido]) REFERENCES [dbo].[difunto]([id_difunto]);

ALTER TABLE [dbo].[difunto] ADD CONSTRAINT FK_Difunto_Cliente FOREIGN KEY ([idCliente]) REFERENCES [dbo].[Cliente]([id_cliente]);

ALTER TABLE [dbo].[Usuario] ADD CONSTRAINT FK_Usuario_Personal FOREIGN KEY ([idPersonal]) REFERENCES [dbo].[Personal]([idPersonal]);

ALTER TABLE [dbo].[MovimientoInventario] ADD CONSTRAINT FK_MovInv_Producto FOREIGN KEY ([idProducto]) REFERENCES [dbo].[Producto]([idProducto]);
ALTER TABLE [dbo].[MovimientoInventario] ADD CONSTRAINT FK_MovInv_Servicio FOREIGN KEY ([idServicio]) REFERENCES [dbo].[ServicioFunerario]([idServicio]);
ALTER TABLE [dbo].[MovimientoInventario] ADD CONSTRAINT FK_MovInv_Personal FOREIGN KEY ([idPersonal]) REFERENCES [dbo].[Personal]([idPersonal]);

ALTER TABLE [dbo].[ServicioFunerario] ADD CONSTRAINT FK_Servicio_Cliente FOREIGN KEY ([idCliente]) REFERENCES [dbo].[Cliente]([id_cliente]);
ALTER TABLE [dbo].[ServicioFunerario] ADD CONSTRAINT FK_Servicio_Fallecido FOREIGN KEY ([idFallecido]) REFERENCES [dbo].[difunto]([id_difunto]);

ALTER TABLE [dbo].[ServicioPersonal] ADD CONSTRAINT FK_ServPersonal_Servicio FOREIGN KEY ([idServicio]) REFERENCES [dbo].[ServicioFunerario]([idServicio]);
ALTER TABLE [dbo].[ServicioPersonal] ADD CONSTRAINT FK_ServPersonal_Personal FOREIGN KEY ([idPersonal]) REFERENCES [dbo].[Personal]([idPersonal]);

ALTER TABLE [dbo].[ReservaSala] ADD CONSTRAINT FK_ReservaSala_Servicio FOREIGN KEY ([idServicio]) REFERENCES [dbo].[ServicioFunerario]([idServicio]);
ALTER TABLE [dbo].[ReservaSala] ADD CONSTRAINT FK_ReservaSala_Sala FOREIGN KEY ([idSala]) REFERENCES [dbo].[Sala]([idSala]);

ALTER TABLE [dbo].[AsignacionVehiculo] ADD CONSTRAINT FK_AsigVeh_Servicio FOREIGN KEY ([idServicio]) REFERENCES [dbo].[ServicioFunerario]([idServicio]);
ALTER TABLE [dbo].[AsignacionVehiculo] ADD CONSTRAINT FK_AsigVeh_Vehiculo FOREIGN KEY ([idVehiculo]) REFERENCES [dbo].[Vehiculo]([idVehiculo]);
ALTER TABLE [dbo].[AsignacionVehiculo] ADD CONSTRAINT FK_AsigVeh_Personal FOREIGN KEY ([idPersonal]) REFERENCES [dbo].[Personal]([idPersonal]);

ALTER TABLE [dbo].[DetalleServicioProducto] ADD CONSTRAINT FK_DetServ_Servicio FOREIGN KEY ([idServicio]) REFERENCES [dbo].[ServicioFunerario]([idServicio]);
ALTER TABLE [dbo].[DetalleServicioProducto] ADD CONSTRAINT FK_DetServ_Producto FOREIGN KEY ([idProducto]) REFERENCES [dbo].[Producto]([idProducto]);

ALTER TABLE [dbo].[DestinoFinal] ADD CONSTRAINT FK_DestinoFinal_Servicio FOREIGN KEY ([idServicio]) REFERENCES [dbo].[ServicioFunerario]([idServicio]);

ALTER TABLE [dbo].[Cotizacion] ADD CONSTRAINT FK_Cotizacion_Servicio FOREIGN KEY ([idServicio]) REFERENCES [dbo].[ServicioFunerario]([idServicio]);

ALTER TABLE [dbo].[Comprobante] ADD CONSTRAINT FK_Comprobante_Servicio FOREIGN KEY ([idServicio]) REFERENCES [dbo].[ServicioFunerario]([idServicio]);

ALTER TABLE [dbo].[Pago] ADD CONSTRAINT FK_Pago_Comprobante FOREIGN KEY ([idComprobante]) REFERENCES [dbo].[Comprobante]([idComprobante]);
GO

-- ============================================================
-- FOREIGN KEYS DE AUDITORIA
-- (todas apuntan a Usuario; Usuario NO se referencia a si misma
--  con FK real para no bloquear la insercion del primer usuario)
-- ============================================================

ALTER TABLE [dbo].[Rol] ADD CONSTRAINT FK_Rol_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Rol] ADD CONSTRAINT FK_Rol_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Rol] ADD CONSTRAINT FK_Rol_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[Modulo] ADD CONSTRAINT FK_Modulo_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Modulo] ADD CONSTRAINT FK_Modulo_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Modulo] ADD CONSTRAINT FK_Modulo_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[RolModulo] ADD CONSTRAINT FK_RolModulo_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[RolModulo] ADD CONSTRAINT FK_RolModulo_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[RolModulo] ADD CONSTRAINT FK_RolModulo_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[Cliente] ADD CONSTRAINT FK_Cliente_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Cliente] ADD CONSTRAINT FK_Cliente_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Cliente] ADD CONSTRAINT FK_Cliente_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[difunto] ADD CONSTRAINT FK_Difunto_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[difunto] ADD CONSTRAINT FK_Difunto_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[difunto] ADD CONSTRAINT FK_Difunto_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[Personal] ADD CONSTRAINT FK_Personal_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Personal] ADD CONSTRAINT FK_Personal_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Personal] ADD CONSTRAINT FK_Personal_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[DocumentoFallecido] ADD CONSTRAINT FK_DocFall_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[DocumentoFallecido] ADD CONSTRAINT FK_DocFall_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[DocumentoFallecido] ADD CONSTRAINT FK_DocFall_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[Vehiculo] ADD CONSTRAINT FK_Vehiculo_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Vehiculo] ADD CONSTRAINT FK_Vehiculo_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Vehiculo] ADD CONSTRAINT FK_Vehiculo_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[Sala] ADD CONSTRAINT FK_Sala_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Sala] ADD CONSTRAINT FK_Sala_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Sala] ADD CONSTRAINT FK_Sala_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[Producto] ADD CONSTRAINT FK_Producto_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Producto] ADD CONSTRAINT FK_Producto_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Producto] ADD CONSTRAINT FK_Producto_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[MovimientoInventario] ADD CONSTRAINT FK_MovInv_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[MovimientoInventario] ADD CONSTRAINT FK_MovInv_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[MovimientoInventario] ADD CONSTRAINT FK_MovInv_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[ServicioFunerario] ADD CONSTRAINT FK_Servicio_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[ServicioFunerario] ADD CONSTRAINT FK_Servicio_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[ServicioFunerario] ADD CONSTRAINT FK_Servicio_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[ServicioPersonal] ADD CONSTRAINT FK_ServPersonal_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[ServicioPersonal] ADD CONSTRAINT FK_ServPersonal_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[ServicioPersonal] ADD CONSTRAINT FK_ServPersonal_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[ReservaSala] ADD CONSTRAINT FK_ReservaSala_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[ReservaSala] ADD CONSTRAINT FK_ReservaSala_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[ReservaSala] ADD CONSTRAINT FK_ReservaSala_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[AsignacionVehiculo] ADD CONSTRAINT FK_AsigVeh_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[AsignacionVehiculo] ADD CONSTRAINT FK_AsigVeh_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[AsignacionVehiculo] ADD CONSTRAINT FK_AsigVeh_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[DetalleServicioProducto] ADD CONSTRAINT FK_DetServ_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[DetalleServicioProducto] ADD CONSTRAINT FK_DetServ_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[DetalleServicioProducto] ADD CONSTRAINT FK_DetServ_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[DestinoFinal] ADD CONSTRAINT FK_DestinoFinal_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[DestinoFinal] ADD CONSTRAINT FK_DestinoFinal_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[DestinoFinal] ADD CONSTRAINT FK_DestinoFinal_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[Cotizacion] ADD CONSTRAINT FK_Cotizacion_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Cotizacion] ADD CONSTRAINT FK_Cotizacion_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Cotizacion] ADD CONSTRAINT FK_Cotizacion_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[Comprobante] ADD CONSTRAINT FK_Comprobante_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Comprobante] ADD CONSTRAINT FK_Comprobante_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Comprobante] ADD CONSTRAINT FK_Comprobante_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);

ALTER TABLE [dbo].[Pago] ADD CONSTRAINT FK_Pago_UsrCrea FOREIGN KEY ([idUsuarioCreacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Pago] ADD CONSTRAINT FK_Pago_UsrMod FOREIGN KEY ([idUsuarioModificacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
ALTER TABLE [dbo].[Pago] ADD CONSTRAINT FK_Pago_UsrElim FOREIGN KEY ([idUsuarioEliminacion]) REFERENCES [dbo].[Usuario]([id_usuario]);
GO

-- ============================================================
-- SEED: USUARIO ADMINISTRADOR INICIAL
-- El password NUNCA se guarda en texto plano: aqui va el hash
-- BCrypt (formato $2a$/$2b$, compatible con el
-- BCryptPasswordEncoder de Spring Security que usa el backend).
-- AuthServiceImpl hace passwordEncoder.matches(rawPassword, hash),
-- por lo que este valor debe ser el resultado de encode(), no la
-- contrasena original.
--
-- Personal.idUsuarioCreacion exige un Usuario existente (FK) y
-- Usuario.idPersonal exige un Personal existente (FK): para el
-- primer registro es un ciclo, asi que se desactiva momentaneamente
-- FK_Personal_UsrCrea, se insertan ambas filas y se vuelve a
-- validar la constraint al final.
-- ============================================================

ALTER TABLE [dbo].[Personal] NOCHECK CONSTRAINT FK_Personal_UsrCrea;

DECLARE @idPersonalAdmin INT;
DECLARE @idUsuarioAdmin INT;

INSERT INTO [dbo].[Personal] (
    [nombres], [apellidos], [dni], [telefono], [direccion], [cargo], [estado],
    [fechaCreacion], [idUsuarioCreacion]
)
VALUES (
    'Admin', 'Sistema', NULL, NULL, NULL, 'Administrador', 'ACTIVO',
    GETDATE(), 1
);

SET @idPersonalAdmin = SCOPE_IDENTITY();

-- Hash BCrypt de la contrasena del admin (generado localmente, rounds=10)
INSERT INTO [dbo].[Usuario] (
    [idPersonal], [email], [password], [rol], [estado],
    [fechaCreacion], [idUsuarioCreacion]
)
VALUES (
    @idPersonalAdmin,
    'admin@blancaflor.com',
    '$2b$10$O0fidwosxc7op3rss.ZAMOpdbcCDEiUXIiQQNTtD.K5N5SA1Uneaq',
    'ADMIN',
    1,
    GETDATE(),
    1
);

SET @idUsuarioAdmin = SCOPE_IDENTITY();

-- Corrige idUsuarioCreacion de Personal para que apunte al admin real
-- (antes tenia el placeholder 1 mientras la FK estaba desactivada)
UPDATE [dbo].[Personal]
SET [idUsuarioCreacion] = @idUsuarioAdmin
WHERE [idPersonal] = @idPersonalAdmin;

ALTER TABLE [dbo].[Personal] WITH CHECK CHECK CONSTRAINT FK_Personal_UsrCrea;
GO
