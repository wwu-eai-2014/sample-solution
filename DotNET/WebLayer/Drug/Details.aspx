﻿<%@ Page Title="C.Sharpe - Drug Details" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" 
        CodeBehind="Details.aspx.cs" Inherits="WebLayer.Drug.Details" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentHolder" runat="server">
<form id="DrugDetailsForm" runat="server">
    <asp:ObjectDataSource ID="DrugDataSource" runat="server"
        SelectMethod="GetDrug" UpdateMethod="UpdateDrug"
        TypeName="Pharmacy.BusinessLayer.Logic.DrugService"
        DataObjectTypeName="Pharmacy.BusinessLayer.Data.Drug">
        <SelectParameters>
            <asp:QueryStringParameter Name="pzn" QueryStringField="pzn" Type="Int32" />
        </SelectParameters>
    </asp:ObjectDataSource>
    <asp:DetailsView ID="DrugDetailsView" runat="server" AutoGenerateRows="False"
        BackColor="White" BorderColor="#CCCCCC" BorderStyle="None" BorderWidth="1px" CellPadding="4"
        ForeColor="Black" GridLines="Horizontal" Height="50px" 
        DataSourceID="DrugDataSource" >
        <EditRowStyle BackColor="#CC3333" Font-Bold="True" ForeColor="White" />
        <Fields>
            <asp:BoundField DataField="PZN" HeaderText="PZN" ReadOnly="True" SortExpression="PZN" />
            <asp:TemplateField HeaderText="Name">
                <ItemTemplate>
                    <asp:Label ID="Name" runat="server" Text='<%# Eval("Name") %>' />
                    </ItemTemplate>
                <EditItemTemplate>
                    <asp:TextBox ID="Name" runat="server" Text='<%# Bind("Name") %>' TextMode="SingleLine" ReadOnly="false" />
                    <asp:RequiredFieldValidator ID="NameBoxValidator" runat="server" 
                        ControlToValidate="Name" ErrorMessage="RequiredFieldValidator"
                        EnableClientScript="false">
                        <span class="error">Name required</span>
                    </asp:RequiredFieldValidator>
                </EditItemTemplate>
            </asp:TemplateField>
            <asp:TemplateField HeaderText="Description">
                <ItemTemplate>
                    <asp:Label ID="Description" runat="server" Text='<%# Eval("Description") %>' />
                </ItemTemplate>
                <EditItemTemplate>
                    <asp:TextBox ID="Description" runat="server" Text='<%# Bind("Description") %>' TextMode="MultiLine" ReadOnly="false" />
                </EditItemTemplate>
            </asp:TemplateField>
            <asp:BoundField DataField="Stock" HeaderText="In Stock" ReadOnly="True" SortExpression="Stock" />
            <asp:TemplateField HeaderText="Minimum Inventory Level">
                <ItemTemplate>
                    <asp:Label ID="MinimumInventoryLevel" runat="server" Text='<%# Eval("MinimumInventoryLevel") %>' />
                </ItemTemplate>
                <EditItemTemplate>
                    <asp:TextBox ID="MinimumInventoryLevel" runat="server" Text='<%# Bind("MinimumInventoryLevel") %>' TextMode="SingleLine" Columns="5" ReadOnly="false" />
                    </EditItemTemplate>
            </asp:TemplateField>
            <asp:TemplateField HeaderText="Optimal Inventory Level">
                <ItemTemplate>
                    <asp:Label ID="OptimalInventoryLevel" runat="server" Text='<%# Eval("OptimalInventoryLevel") %>' />
                </ItemTemplate>
                <EditItemTemplate>
                    <asp:TextBox ID="OptimalInventoryLevel" runat="server" Text='<%# Bind("OptimalInventoryLevel") %>' TextMode="SingleLine" Columns="5" ReadOnly="false" />
                    </EditItemTemplate>
            </asp:TemplateField>
            <asp:CommandField ButtonType="Button" ShowEditButton="true" CancelText="Cancel" EditText="Edit" UpdateText="Update" />
        </Fields>
        <FooterStyle BackColor="#CCCC99" ForeColor="Black" />
        <HeaderStyle BackColor="#333333" Font-Bold="True" ForeColor="White" />
        <PagerStyle BackColor="White" ForeColor="Black" HorizontalAlign="Right" />
    </asp:DetailsView>
    <h3>Pending Replenishment Orders</h3>
    <asp:ObjectDataSource ID="PositionDatasource" runat="server"
        SelectMethod="GetPendingPositionsForDrug"
        TypeName="Pharmacy.BusinessLayer.Logic.OrderService"
        DataObjectTypeName="Pharmacy.BusinessLayer.Data.Position">
        <SelectParameters>
            <asp:QueryStringParameter Name="pzn" QueryStringField="pzn" Type="Int32" />
        </SelectParameters>
    </asp:ObjectDataSource>
    <asp:GridView ID="PendingOrdersGridView" runat="server" AutoGenerateColumns="False" DataSourceID="PositionDatasource">
        <Columns>
            <asp:BoundField DataField="Order.Id" HeaderText="Order Id" ReadOnly="True" SortExpression="Order.Id" />            
            <asp:BoundField DataField="Order.State" HeaderText="State" ReadOnly="True" SortExpression="Order.State" />
            <asp:BoundField DataField="Order.ExpectedDelivery" HeaderText="Expected delivery" ReadOnly="True" SortExpression="Order.ExpectedDelivery"
                DataFormatString="{0:dd.MM.yyyy HH:mm}" />
            <asp:BoundField DataField="Quantity" HeaderText="Quantity" ReadOnly="True" SortExpression="Quantity" />
            <asp:TemplateField>
                <ItemTemplate>
                    <asp:HyperLink ID="OrderDetailsLink" runat="server" Text="Details"
                        NavigateUrl='<%# String.Format("~/ReplenishmentOrder/Details.aspx?id={0}", Eval("Order.Id")) %>' />
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
        <EmptyDataTemplate>
            No pending replenishment orders available.
        </EmptyDataTemplate>
    </asp:GridView>
</form>
</asp:Content>