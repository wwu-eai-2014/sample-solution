
-- --------------------------------------------------
-- Entity Designer DDL Script for SQL Server 2005, 2008, and Azure
-- --------------------------------------------------
-- Date Created: 06/06/2013 13:14:13
-- Generated from EDMX file: C:\Users\jan\documents\visual studio 2012\Projects\Pharmacy\BusinessLayer\Data\Pharmacy.edmx
-- --------------------------------------------------

SET QUOTED_IDENTIFIER OFF;
GO
USE [PharmacyEF];
GO
IF SCHEMA_ID(N'dbo') IS NULL EXECUTE(N'CREATE SCHEMA [dbo]');
GO

-- --------------------------------------------------
-- Dropping existing FOREIGN KEY constraints
-- --------------------------------------------------

IF OBJECT_ID(N'[dbo].[FK_DrugInventoryEvent]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[InventoryEventSet] DROP CONSTRAINT [FK_DrugInventoryEvent];
GO
IF OBJECT_ID(N'[dbo].[FK_DrugPosition]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[PositionSet] DROP CONSTRAINT [FK_DrugPosition];
GO
IF OBJECT_ID(N'[dbo].[FK_ReplenishmentOrderPosition]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[PositionSet] DROP CONSTRAINT [FK_ReplenishmentOrderPosition];
GO
IF OBJECT_ID(N'[dbo].[FK_WithdrawEvent_inherits_InventoryEvent]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[InventoryEventSet_WithdrawEvent] DROP CONSTRAINT [FK_WithdrawEvent_inherits_InventoryEvent];
GO
IF OBJECT_ID(N'[dbo].[FK_RestockEvent_inherits_InventoryEvent]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[InventoryEventSet_RestockEvent] DROP CONSTRAINT [FK_RestockEvent_inherits_InventoryEvent];
GO
IF OBJECT_ID(N'[dbo].[FK_ReplenishEvent_inherits_InventoryEvent]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[InventoryEventSet_ReplenishEvent] DROP CONSTRAINT [FK_ReplenishEvent_inherits_InventoryEvent];
GO

-- --------------------------------------------------
-- Dropping existing tables
-- --------------------------------------------------

IF OBJECT_ID(N'[dbo].[DrugSet]', 'U') IS NOT NULL
    DROP TABLE [dbo].[DrugSet];
GO
IF OBJECT_ID(N'[dbo].[InventoryEventSet]', 'U') IS NOT NULL
    DROP TABLE [dbo].[InventoryEventSet];
GO
IF OBJECT_ID(N'[dbo].[PositionSet]', 'U') IS NOT NULL
    DROP TABLE [dbo].[PositionSet];
GO
IF OBJECT_ID(N'[dbo].[ReplenishmentOrderSet]', 'U') IS NOT NULL
    DROP TABLE [dbo].[ReplenishmentOrderSet];
GO
IF OBJECT_ID(N'[dbo].[InventoryEventSet_WithdrawEvent]', 'U') IS NOT NULL
    DROP TABLE [dbo].[InventoryEventSet_WithdrawEvent];
GO
IF OBJECT_ID(N'[dbo].[InventoryEventSet_RestockEvent]', 'U') IS NOT NULL
    DROP TABLE [dbo].[InventoryEventSet_RestockEvent];
GO
IF OBJECT_ID(N'[dbo].[InventoryEventSet_ReplenishEvent]', 'U') IS NOT NULL
    DROP TABLE [dbo].[InventoryEventSet_ReplenishEvent];
GO

-- --------------------------------------------------
-- Creating all tables
-- --------------------------------------------------

-- Creating table 'DrugSet'
CREATE TABLE [dbo].[DrugSet] (
    [PZN] int  NOT NULL,
    [Name] nvarchar(max)  NOT NULL,
    [Description] nvarchar(max)  NULL,
    [Stock] int  NOT NULL,
    [MinimumInventoryLevel] int  NOT NULL,
    [OptimalInventoryLevel] int  NOT NULL
);
GO

-- Creating table 'InventoryEventSet'
CREATE TABLE [dbo].[InventoryEventSet] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [Quantity] int  NOT NULL,
    [DateOfAction] datetime  NOT NULL,
    [DrugPZN] int  NOT NULL
);
GO

-- Creating table 'PositionSet'
CREATE TABLE [dbo].[PositionSet] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [Quantity] int  NOT NULL,
    [DrugPZN] int  NOT NULL,
    [ReplenishmentOrderId] int  NOT NULL
);
GO

-- Creating table 'ReplenishmentOrderSet'
CREATE TABLE [dbo].[ReplenishmentOrderSet] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [State] int  NOT NULL,
    [ExpectedDelivery] datetime  NULL,
    [ActualDelivery] datetime  NULL
);
GO

-- Creating table 'InventoryEventSet_WithdrawEvent'
CREATE TABLE [dbo].[InventoryEventSet_WithdrawEvent] (
    [Id] int  NOT NULL
);
GO

-- Creating table 'InventoryEventSet_RestockEvent'
CREATE TABLE [dbo].[InventoryEventSet_RestockEvent] (
    [Id] int  NOT NULL
);
GO

-- Creating table 'InventoryEventSet_ReplenishEvent'
CREATE TABLE [dbo].[InventoryEventSet_ReplenishEvent] (
    [Id] int  NOT NULL
);
GO

-- --------------------------------------------------
-- Creating all PRIMARY KEY constraints
-- --------------------------------------------------

-- Creating primary key on [PZN] in table 'DrugSet'
ALTER TABLE [dbo].[DrugSet]
ADD CONSTRAINT [PK_DrugSet]
    PRIMARY KEY CLUSTERED ([PZN] ASC);
GO

-- Creating primary key on [Id] in table 'InventoryEventSet'
ALTER TABLE [dbo].[InventoryEventSet]
ADD CONSTRAINT [PK_InventoryEventSet]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'PositionSet'
ALTER TABLE [dbo].[PositionSet]
ADD CONSTRAINT [PK_PositionSet]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'ReplenishmentOrderSet'
ALTER TABLE [dbo].[ReplenishmentOrderSet]
ADD CONSTRAINT [PK_ReplenishmentOrderSet]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'InventoryEventSet_WithdrawEvent'
ALTER TABLE [dbo].[InventoryEventSet_WithdrawEvent]
ADD CONSTRAINT [PK_InventoryEventSet_WithdrawEvent]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'InventoryEventSet_RestockEvent'
ALTER TABLE [dbo].[InventoryEventSet_RestockEvent]
ADD CONSTRAINT [PK_InventoryEventSet_RestockEvent]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'InventoryEventSet_ReplenishEvent'
ALTER TABLE [dbo].[InventoryEventSet_ReplenishEvent]
ADD CONSTRAINT [PK_InventoryEventSet_ReplenishEvent]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- --------------------------------------------------
-- Creating all FOREIGN KEY constraints
-- --------------------------------------------------

-- Creating foreign key on [DrugPZN] in table 'InventoryEventSet'
ALTER TABLE [dbo].[InventoryEventSet]
ADD CONSTRAINT [FK_DrugInventoryEvent]
    FOREIGN KEY ([DrugPZN])
    REFERENCES [dbo].[DrugSet]
        ([PZN])
    ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Creating non-clustered index for FOREIGN KEY 'FK_DrugInventoryEvent'
CREATE INDEX [IX_FK_DrugInventoryEvent]
ON [dbo].[InventoryEventSet]
    ([DrugPZN]);
GO

-- Creating foreign key on [DrugPZN] in table 'PositionSet'
ALTER TABLE [dbo].[PositionSet]
ADD CONSTRAINT [FK_DrugPosition]
    FOREIGN KEY ([DrugPZN])
    REFERENCES [dbo].[DrugSet]
        ([PZN])
    ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Creating non-clustered index for FOREIGN KEY 'FK_DrugPosition'
CREATE INDEX [IX_FK_DrugPosition]
ON [dbo].[PositionSet]
    ([DrugPZN]);
GO

-- Creating foreign key on [ReplenishmentOrderId] in table 'PositionSet'
ALTER TABLE [dbo].[PositionSet]
ADD CONSTRAINT [FK_ReplenishmentOrderPosition]
    FOREIGN KEY ([ReplenishmentOrderId])
    REFERENCES [dbo].[ReplenishmentOrderSet]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Creating non-clustered index for FOREIGN KEY 'FK_ReplenishmentOrderPosition'
CREATE INDEX [IX_FK_ReplenishmentOrderPosition]
ON [dbo].[PositionSet]
    ([ReplenishmentOrderId]);
GO

-- Creating foreign key on [Id] in table 'InventoryEventSet_WithdrawEvent'
ALTER TABLE [dbo].[InventoryEventSet_WithdrawEvent]
ADD CONSTRAINT [FK_WithdrawEvent_inherits_InventoryEvent]
    FOREIGN KEY ([Id])
    REFERENCES [dbo].[InventoryEventSet]
        ([Id])
    ON DELETE CASCADE ON UPDATE NO ACTION;
GO

-- Creating foreign key on [Id] in table 'InventoryEventSet_RestockEvent'
ALTER TABLE [dbo].[InventoryEventSet_RestockEvent]
ADD CONSTRAINT [FK_RestockEvent_inherits_InventoryEvent]
    FOREIGN KEY ([Id])
    REFERENCES [dbo].[InventoryEventSet]
        ([Id])
    ON DELETE CASCADE ON UPDATE NO ACTION;
GO

-- Creating foreign key on [Id] in table 'InventoryEventSet_ReplenishEvent'
ALTER TABLE [dbo].[InventoryEventSet_ReplenishEvent]
ADD CONSTRAINT [FK_ReplenishEvent_inherits_InventoryEvent]
    FOREIGN KEY ([Id])
    REFERENCES [dbo].[InventoryEventSet]
        ([Id])
    ON DELETE CASCADE ON UPDATE NO ACTION;
GO

-- --------------------------------------------------
-- Script has ended
-- --------------------------------------------------