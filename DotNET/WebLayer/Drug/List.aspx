<%@ Page Title="C.Sharpe - Drug List" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" 
        CodeBehind="List.aspx.cs" Inherits="WebLayer.Drug.List" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentHolder" runat="server">
    <form id="DrugListForm" runat="server">
        <asp:ObjectDataSource ID="AllDrugsDatasource" runat="server" SelectMethod="GetAllDrugs" TypeName="Pharmacy.BusinessLayer.Logic.DrugService"></asp:ObjectDataSource>
        <asp:GridView ID="DrugGridView" runat="server" AutoGenerateColumns="False" DataSourceID="AllDrugsDatasource">
            <Columns>
                <asp:BoundField DataField="PZN" HeaderText="PZN" ReadOnly="True" SortExpression="PZN" />
                <asp:BoundField DataField="Name" HeaderText="Name" SortExpression="Name" />
                <asp:BoundField DataField="Description" HeaderText="Description" SortExpression="Description" />
                <asp:BoundField DataField="Stock" HeaderText="Stock" ReadOnly="True" SortExpression="Stock" />
                <asp:HyperLinkField DataNavigateUrlFields="PZN" DataNavigateUrlFormatString="Details.aspx?pzn={0}" Text="Details" />
            </Columns>
            <EmptyDataTemplate>
                No drugs yet.
            </EmptyDataTemplate>
        </asp:GridView>
    </form>
</asp:Content>