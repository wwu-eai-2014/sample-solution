<%@ Page Title="C.Sharpe - Replenishment Order List" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Details.aspx.cs" Inherits="WebLayer.ReplenishmentOrder.Details" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentHolder" runat="server">
<form id="OrderDetailsForm" runat="server">
    <asp:ObjectDataSource ID="OrderDataSource" runat="server"
        SelectMethod="GetOrder"
        TypeName="Pharmacy.BusinessLayer.Logic.OrderService"
        DataObjectTypeName="Pharmacy.BusinessLayer.Data.ReplenishmentOrder">
        <SelectParameters>
            <asp:QueryStringParameter Name="ID" QueryStringField="id" Type="Int32" />
        </SelectParameters>
    </asp:ObjectDataSource>
    <asp:DetailsView ID="OrderDetailsView" runat="server" AutoGenerateRows="false"
        BackColor="White" BorderColor="#CCCCCC" BorderStyle="None" BorderWidth="1px" CellPadding="4"
        ForeColor="Black" GridLines="Horizontal" Height="50px" 
        DataSourceID="OrderDataSource">
    </asp:DetailsView>
</form>
</asp:Content>
