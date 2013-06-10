<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="List.aspx.cs" Inherits="WebLayer.Main" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
    
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
    
    </div>
    </form>
</body>
</html>
