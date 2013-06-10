<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Details.aspx.cs" Inherits="WebLayer.Drug.Details" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        
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
        
    </div>
    </form>
</body>
</html>
