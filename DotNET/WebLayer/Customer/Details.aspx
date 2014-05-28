<%@ Page Title="C.Sharpe - Customer Details" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true"
    CodeBehind="Details.aspx.cs" Inherits="WebLayer.Customer.Details" %>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentHolder" runat="server">
    <form id="CustomerDetailsForm" runat="server">
        <asp:ObjectDataSource ID="CustomerDataSource" runat="server"
            SelectMethod="GetCustomer" UpdateMethod="UpdateCustomer"
            TypeName="Pharmacy.BusinessLayer.Logic.CustomerService"
            DataObjectTypeName="Pharmacy.BusinessLayer.Data.Customer">
            <SelectParameters>
                <asp:QueryStringParameter Name="id" QueryStringField="id" Type="Int32" />
            </SelectParameters>
        </asp:ObjectDataSource>
        <asp:DetailsView ID="CustomerDetailsView" runat="server" AutoGenerateRows="false"
            BackColor="White" BorderColor="#CCCCCC" BorderStyle="None" BorderWidth="1px" CellPadding="4"
            ForeColor="Black" GridLines="Horizontal" Height="50px" 
            DataSourceID="CustomerDataSource">
            <EditRowStyle BackColor="#CC3333" Font-Bold="True" ForeColor="White" />
            <Fields>
                <asp:BoundField DataField="Id" HeaderText="Customer Id" ReadOnly="true" />
                <asp:BoundField DataField="Name" HeaderText="Name" ReadOnly="true" />
                <asp:TemplateField HeaderText="Telephone number">
                    <ItemTemplate>
                        <asp:Label ID="TelephoneNumber" runat="server" Text='<%# Eval("TelephoneNumber") %>' />
                    </ItemTemplate>
                    <EditItemTemplate>
                        <asp:TextBox ID="TelephoneNumber" runat="server" Text='<%# Bind("TelephoneNumber") %>' />
                        <asp:RequiredFieldValidator ID="TelephoneNumberValidator" runat="server" 
                            ControlToValidate="TelephoneNumber" ErrorMessage="RequiredFieldValidator"
                            EnableClientScript="false">
                            <span class="error">Telephone number required</span>
                        </asp:RequiredFieldValidator>
                   </EditItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField HeaderText="Address">
                    <ItemTemplate>
                        <asp:Label ID="Address" runat="server" Text='<%# Eval("Address") %>' />
                    </ItemTemplate>
                    <EditItemTemplate>
                        <asp:TextBox ID="Address" runat="server" Text='<%# Bind("Address") %>' TextMode="MultiLine" ReadOnly="false" />
                    </EditItemTemplate>
                </asp:TemplateField>
                <asp:CommandField ButtonType="Button" ShowEditButton="true" CancelText="Cancel" EditText="Edit" UpdateText="Update" />
            </Fields>
            <FooterStyle BackColor="#CCCC99" ForeColor="Black" />
            <HeaderStyle BackColor="#333333" Font-Bold="True" ForeColor="White" />
            <PagerStyle BackColor="White" ForeColor="Black" HorizontalAlign="Right" />
        </asp:DetailsView>
    </form>
</asp:Content>
