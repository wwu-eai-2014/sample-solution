﻿<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true"
    CodeBehind="Details.aspx.cs" Inherits="WebLayer.Prescription.Details" %>
<%@ Import Namespace="Pharmacy.BusinessLayer.Data" %>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentHolder" runat="server">
    <form id="PrescriptionDetailsForm" runat="server">
        <asp:ObjectDataSource ID="PrescriptionDataSource" runat="server"
            SelectMethod="GetPrescription"
            TypeName="Pharmacy.BusinessLayer.Logic.PrescriptionService"
            DataObjectTypeName="Pharmacy.BusinessLayer.Data.Prescription">
            <SelectParameters>
                <asp:QueryStringParameter Name="Id" QueryStringField="id" Type="Int32" />
            </SelectParameters>
        </asp:ObjectDataSource>
        <asp:DetailsView ID="PrescriptionDetailsView" runat="server" AutoGenerateRows="false"
            BackColor="White" BorderColor="#CCCCCC" BorderStyle="None" BorderWidth="1px" CellPadding="4"
            ForeColor="Black" GridLines="Horizontal" Height="50px" 
            DataSourceID="PrescriptionDataSource">
            <Fields>
                <asp:BoundField DataField="Id" HeaderText="Prescription" ReadOnly="true" />
                <asp:TemplateField HeaderText="Customer">
                    <ItemTemplate>
                        <asp:HyperLink runat="server" ID="CustomerDetailsLink"
                            NavigateUrl='<%# String.Format("~/Customer/Details.aspx?id={0}", Eval("Customer.Id")) %>'
                            Text='<%# Eval("Customer.Name") %>' />
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField DataField="State" HeaderText="State" />
                <asp:TemplateField HeaderText="Issuer">
                    <ItemTemplate>
                        <asp:Label ID="IssuerLabel" runat="server"
                            Text='<%# Eval("IssuingPhysician") %>' 
                            Visible='<%# ((PrescriptionState)Eval("State")) != PrescriptionState.Entry %>' />
                        <asp:TextBox ID="IssuerBox" TextMode="SingleLine" runat="server"
                            Text='<%# Eval("IssuingPhysician") %>'
                            Visible='<%# ((PrescriptionState)Eval("State")) == PrescriptionState.Entry %>' />
                        <asp:RequiredFieldValidator ID="RequiredIssuer" ControlToValidate="IssuerBox"
                            EnableClientScript="false" runat="server">
                            Issuer required
                        </asp:RequiredFieldValidator>
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField HeaderText="Issued on">
                    <ItemTemplate>
                        <asp:Label ID="IssuedOnLabel" runat="server"
                            Text='<%# Eval("IssueDate", "{0:dd.MM.yyyy}") %>' 
                            Visible='<%# ((PrescriptionState)Eval("State")) != PrescriptionState.Entry %>' />
                        <asp:TextBox ID="IssuedOnBox" TextMode="DateTime" runat="server"
                            Text='<%# Eval("IssueDate", "{0:dd.MM.yyyy}") %>'
                            Visible='<%# ((PrescriptionState)Eval("State")) == PrescriptionState.Entry %>' />
                        <asp:RequiredFieldValidator ID="RequiredIssued" ControlToValidate="IssuedOnBox"
                            EnableClientScript="false" runat="server">
                            Issued on required
                        </asp:RequiredFieldValidator>
                        <asp:RegularExpressionValidator ID="ValidIssuedOn" ControlToValidate="IssuedOnBox"
                            EnableClientScript="false" runat="server"
                            ValidationExpression="\d{2}.\d{2}.\d{4}">
                            Invalid date (e.g. 24.12.2013)
                        </asp:RegularExpressionValidator>
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField HeaderText="Entered on">
                    <ItemTemplate>
                        <asp:Label ID="EnteredOnLabel" runat="server"
                            Text='<%# Eval("EntryDate", "{0:dd.MM.yyyy HH:mm}") %>' 
                            Visible='<%# ((PrescriptionState)Eval("State")) != PrescriptionState.Entry %>' />
                        <asp:TextBox ID="EnteredOnBox" TextMode="DateTime" runat="server"
                            Text='<%# Eval("EntryDate", "{0:dd.MM.yyyy HH:mm}") %>'
                            Visible='<%# ((PrescriptionState)Eval("State")) == PrescriptionState.Entry %>' />
                        <asp:RequiredFieldValidator ID="RequiredEnteredOn" ControlToValidate="EnteredOnBox"
                            EnableClientScript="false" runat="server">
                            Entered on required
                        </asp:RequiredFieldValidator>
                        <asp:RegularExpressionValidator ID="ValidEnteredOn" ControlToValidate="EnteredOnBox"
                            EnableClientScript="false" runat="server"
                            ValidationExpression="\d{2}.\d{2}.\d{4} \d{2}:\d{2}">
                            Invalid date (e.g. 24.12.2013 18:32)
                        </asp:RegularExpressionValidator>
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:TemplateField>
                    <ItemTemplate>
                        <asp:Button ID="UpdatePrescriptionButton" runat="server" OnCommand="UpdatePrescription_Command"
                            Text="Update prescription" Visible='<%# ((PrescriptionState)Eval("State")) == PrescriptionState.Entry %>' />
                        <asp:Button ID="NextStateButton" runat="server" OnCommand="NextState_Command"
                            Text='<%# "Proceed to " + ((PrescriptionState)Eval("State")).Next() %>'
                            Enabled='<%# ((PrescriptionState)Eval("State")).Proceedable((ICollection<Item>)Eval("Items")) %>'
                            Visible='<%# ((PrescriptionState)Eval("State")).Proceedable((ICollection<Item>)Eval("Items")) %>'
                        />
                        <asp:Button ID="CancelButton" runat="server" OnCommand="Cancel_Command" Text="Cancel"
                            Enabled='<%# ((PrescriptionState)Eval("State")).Cancellable() %>'
                            Visible='<%# ((PrescriptionState)Eval("State")).Cancellable() %>' />
                    </ItemTemplate>
                </asp:TemplateField>
            </Fields>
        </asp:DetailsView>
    </form>
</asp:Content>
