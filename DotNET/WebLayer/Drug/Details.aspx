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
            SelectMethod="GetDrug" UpdateMethod="UpdateDrugData"
            TypeName="Pharmacy.BusinessLayer.Logic.DrugService">
            <SelectParameters>
                <asp:QueryStringParameter Name="pzn" QueryStringField="pzn" Type="Int32" />
            </SelectParameters>
        </asp:ObjectDataSource>
        <asp:DetailsView ID="DetailsView1" runat="server" AutoGenerateRows="False"
            BackColor="White" BorderColor="#CCCCCC" BorderStyle="None" BorderWidth="1px" CellPadding="4"
            ForeColor="Black" GridLines="Horizontal" Height="50px" Width="125px"
            DataSourceID="DrugDataSource" >
            <EditRowStyle BackColor="#CC3333" Font-Bold="True" ForeColor="White" />
            <Fields>
                <asp:BoundField DataField="PZN" HeaderText="PZN" ReadOnly="True" SortExpression="PZN" />
                <asp:TemplateField HeaderText="Name">
                    <ItemTemplate>
                        <asp:TextBox ID="Name" runat="server" Text='<%# Bind("Name") %>' TextMode="SingleLine" ReadOnly="true"></asp:TextBox>
                     </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField HeaderText="Description">
                    <ItemTemplate>
                        <asp:TextBox ID="Description" runat="server" Text='<%# Eval("Description") %>' TextMode="MultiLine" ReadOnly="true"></asp:TextBox>
                     </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField DataField="Stock" HeaderText="In Stock" ReadOnly="True" SortExpression="Stock" />
                <asp:TemplateField HeaderText="MinimumInventoryLevel">
                    <ItemTemplate>
                        <asp:TextBox ID="MinimumInventoryLevel" runat="server" Text='<%# Eval("MinimumInventoryLevel") %>' TextMode="SingleLine" Columns="5" ReadOnly="true"></asp:TextBox>
                     </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField HeaderText="OptimalInventoryLevel">
                    <ItemTemplate>
                        <asp:TextBox ID="OptimalInventoryLevel" runat="server" Text='<%# Eval("OptimalInventoryLevel") %>' TextMode="SingleLine" Columns="5" ReadOnly="true"></asp:TextBox>
                     </ItemTemplate>
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
